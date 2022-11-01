package com.example.abcd
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import com.example.abcd.presentation.ui.MainScreenManager
import com.example.abcd.presentation.ui.characters.CharactersFragment
import com.example.abcd.presentation.ui.favorite.FavoriteFragment
import com.example.abcd.presentation.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var screenManager: MainScreenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                screenManager.back()
                return true
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView(){
        screenManager = MainScreenManager()
        screenManager.bind(this@MainActivity, R.id.container, this.findViewById(R.id.topAppBar))
        screenManager.replaceFragment(CharactersFragment(getString(R.string.fragment_list_title)))
        initBottomBar()
    }

    private fun initBottomBar(){
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_list -> {
                    screenManager.replaceFragment(CharactersFragment(getString(R.string.fragment_list_title)))
                    true
                }
                R.id.page_favorite -> {
                    screenManager.replaceFragment(FavoriteFragment(getString(R.string.fragment_favorite_title)))
                    //screenManager.replaceFragment(FavoriteFragment(), R.string.fragment_favorite_title)
                    true
                }
                R.id.page_settings -> {
                    screenManager.replaceFragment(SettingsFragment(getString(R.string.fragment_settings_title)))
                    true
                }
                else -> false
            }
        }
    }

    fun getScreenManager(): MainScreenManager = screenManager

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu?.findItem(R.id.app_bar_search)
        screenManager.addSearch(search)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onBackPressed() {
        screenManager.back()
    }

}