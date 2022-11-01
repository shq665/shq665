package com.example.abcd.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.abcd.MainActivity
import com.example.abcd.presentation.ui.MainScreenManager

abstract class BaseFragment(resId: Int): Fragment(resId) {

    abstract val titleFragment: String
    abstract val hasHomeButton: Boolean
    val isSearch: Boolean = false

    fun getScreenManager(): MainScreenManager? = (activity as? MainActivity)?.getScreenManager()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getScreenManager()?.setSearchViewVisible(false)
    }
}