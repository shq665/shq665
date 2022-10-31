package com.example.abcd.presentation.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.bumptech.glide.Glide
import com.example.abcd.R
import com.example.abcd.databinding.FragmentDetailsBinding
import com.example.abcd.domain.model.CharacterId
import com.example.abcd.presentation.BaseFragment
import com.example.abcd.presentation.ext.loadImageResources
import com.example.abcd.presentation.ext.loadImageUrl
import com.example.abcd.presentation.ext.loadImageUrlWithoutScale
import com.example.abcd.presentation.model.Lce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment(override val titleFragment: String, id: Int) : BaseFragment(R.layout.fragment_details) {
    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel by viewModel<DetailsViewModel>() { parametersOf(id) }
    override val hasHomeButton: Boolean
        get() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewModel.detailsCharacterFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach {cht ->
                    when(cht){
                        is Lce.Loading -> {loading.isVisible = true}
                        is Lce.Content -> {
                            val chInfo = cht.data.characterInfo
                            detailsName.text = cht.data.name
                            detailsNickname.text = chInfo.nickname
                            detailsBirthday.text = chInfo.birthday
                            detailsCategory.text = chInfo.category
                            detailsPortrayed.text = chInfo.portrayed
                            detailsStatus.text = chInfo.status
                            detailsImage.loadImageUrlWithoutScale(cht.data.img)
                            if(cht.data.isFavorite){
                                detailsFavoriteImage.loadImageResources(R.drawable.ic_baseline_bookmark_24)
                            }else detailsFavoriteImage.loadImageResources(R.drawable.ic_baseline_bookmark_border_24)

                            loading.isVisible = false
                            container.isVisible = true

                            detailsFavoriteImage.setOnClickListener {
                                viewModel.onUpdateClicked(cht.data.id.rawId)
                            }
                        }
                        is Lce.Error -> {}
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.updateFavoriteFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach {
                    if(it){
                        detailsFavoriteImage.loadImageResources(R.drawable.ic_baseline_bookmark_24)
                    }else detailsFavoriteImage.loadImageResources(R.drawable.ic_baseline_bookmark_border_24)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

}