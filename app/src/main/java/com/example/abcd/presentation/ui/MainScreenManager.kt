package com.example.abcd.presentation.ui

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.abcd.presentation.BaseFragment
import com.google.android.material.appbar.MaterialToolbar

class MainScreenManager() {

    private var activity: AppCompatActivity? = null
    private var container: Int? = null
    private var toolBar: MaterialToolbar? = null
    private var search: MenuItem? = null
    private var searchView: SearchView? = null
    private var transaction: FragmentTransaction? = null
    private var searchListener: SearchView.OnQueryTextListener? = null

    fun bind(
        _activity: AppCompatActivity,
        _container: Int,
        _toolBar: MaterialToolbar
    ){
        this.activity = _activity
        this.container = _container
        this.toolBar = _toolBar
        this.activity?.setSupportActionBar(toolBar)
        this.transaction = activity?.supportFragmentManager?.beginTransaction()
    }


    fun addFragmentToBackStack(fragment: BaseFragment){
        setDisplayHomeButton(fragment.hasHomeButton)
        if(!checkNotNull()) return
        activity?.supportFragmentManager?.commit {
            container?.let { add(it, fragment) }
            addToBackStack(fragment.titleFragment)
        }
        toolBar?.title = fragment.titleFragment
    }

    fun replaceFragment(fragment: BaseFragment){
        activity?.supportFragmentManager?.clearBackStack("")
        if(!checkNotNull()) return
        setDisplayHomeButton(fragment.hasHomeButton)
        transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            container?.let { replace(it, fragment) }
            addToBackStack(fragment.titleFragment)
            commit()
        }
        toolBar?.title = fragment.titleFragment
    }

    fun back(){
        activity?.supportFragmentManager?.popBackStack().apply {
            val fragments = activity?.supportFragmentManager?.fragments
            val countMainFragments = fragments?.filter { it is BaseFragment }?.size
            fragments?.onEach {
                if(countMainFragments == 1) {
                    activity?.finish()
                    return
                }
                if(it is BaseFragment){
                    toolBar?.title = (it as? BaseFragment)?.titleFragment
                    setDisplayHomeButton(false)
                    return
                }
            }

        }
    }

    fun setDisplayHomeButton(isShow: Boolean){
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        activity?.supportActionBar?.setHomeButtonEnabled(isShow)
    }

    fun addSearch(search: MenuItem?) {
        this.search = search
        this.searchView = search?.actionView as SearchView
        this.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchListener?.onQueryTextChange(newText)
                return true
            }
        })
    }

    fun setSearchViewVisible(isvisible: Boolean){
        search?.isVisible = isvisible
    }

    fun getSearchView(): SearchView? = searchView

    fun registerCallBack(searchListener: SearchView.OnQueryTextListener){
        this.searchListener = searchListener
    }

    private fun checkNotNull() =
        activity  != null && container != null && toolBar != null

}