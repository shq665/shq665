package com.example.abcd.presentation.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.abcd.R
import com.example.abcd.databinding.FragmentFavoriteBinding
import com.example.abcd.domain.model.Character
import com.example.abcd.presentation.BaseFragment
import com.example.abcd.presentation.model.Lce
import com.example.abcd.presentation.ui.characters.CharacterAdapter
import com.example.abcd.presentation.ui.details.DetailsFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment(override val titleFragment: String) : BaseFragment(R.layout.fragment_favorite) {

    private val binding: FragmentFavoriteBinding by viewBinding()
    private val viewModel by viewModel<FavoriteViewModel>()
    override val hasHomeButton: Boolean
        get() = false

    private val adapter by lazy {
        CharacterAdapter(requireContext(), ::clickItemCharacter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            charactersRecyclerView.adapter = adapter

            viewModel.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { lceState ->
                    when(lceState){
                        Lce.Loading  -> {
                            loading.isVisible =  true
                            textEmpty.isVisible = false
                        }
                        is Lce.Content -> {
                            loading.isVisible = false
                            if(lceState.data.isNotEmpty())adapter.submitList(lceState.data)
                            else{
                                textEmpty.isVisible = true
                            }
                        }
                        is Lce.Error -> {
                            Toast.makeText(
                                requireContext(),
                                lceState.throwable.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun clickItemCharacter(ch: Character){
        getScreenManager()?.addFragmentToBackStack(DetailsFragment(ch.name, ch.id.rawId))
    }
}