package com.example.abcd.presentation.ui.content

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.abcd.R
import com.example.abcd.databinding.FragmentContentBinding

class ContentFragment(): Fragment(R.layout.fragment_content) {

    private val binding: FragmentContentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val nestedController =
                (childFragmentManager.findFragmentById(R.id.nav_container)
                        as NavHostFragment).navController
            bottomNavigation.setupWithNavController(nestedController)
        }
    }
}