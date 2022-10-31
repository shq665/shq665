package com.example.abcd.presentation.ui.characters

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.abcd.R
import com.example.abcd.databinding.FragmentCharactersBinding
import com.example.abcd.domain.model.Character
import com.example.abcd.presentation.BaseFragment
import com.example.abcd.presentation.model.Lce
import com.example.abcd.presentation.ui.details.DetailsFragment
import com.example.abcd.presentation.ui.map.MapsFragment
import com.google.android.gms.maps.MapFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment(override val titleFragment: String) : BaseFragment(R.layout.fragment_characters),
    SearchView.OnQueryTextListener {

    private val binding: FragmentCharactersBinding by viewBinding()
    private val viewModel by viewModel<CharactersViewModel>()

    private val adapter by lazy {
        CharacterAdapter(requireContext(), ::clickItemCharacter)
    }
    override val hasHomeButton: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            charactersRecyclerView.adapter = adapter

            refresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                viewModel.onRefreshed()
            }
            viewModel.dataFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { refresh.isRefreshing = false}
                .onEach { lceState ->
                    lceState.map { lce ->
                        when (lce) {
                            Lce.Loading -> {
                                loading.isVisible = true
                            }
                            is Lce.Content -> {
                                loading.isVisible = false
                                refresh.isVisible = true
                                adapter.submitList(lce.data)
                            }
                            is Lce.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    lce.throwable.message, Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            fab.setOnClickListener {
                getScreenManager()?.addFragmentToBackStack(MapsFragment(getString(R.string.fragment_map_title)))
            }
        }
        getScreenManager()?.setSearchViewVisible(true)
        getScreenManager()?.registerCallBack(this)

    }

    private fun clickItemCharacter(ch: Character){
        getScreenManager()?.addFragmentToBackStack(DetailsFragment(ch.name, ch.id.rawId))
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText ?: "")
        return true
    }
}