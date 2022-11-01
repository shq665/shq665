package com.example.abcd.presentation.ui.settings

import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.abcd.BuildConfig
import com.example.abcd.MainActivity
import com.example.abcd.R
import com.example.abcd.databinding.FragmentSettingsBinding
import com.example.abcd.domain.model.ThemeState
import com.example.abcd.presentation.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment(override val titleFragment: String): BaseFragment(R.layout.fragment_settings) {

    override val hasHomeButton: Boolean
        get() = false

    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            viewModel.themeFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach {
                    themeMode.isChecked = it == ThemeState.NIGHT
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            themeMode.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, isDefaultTheme: Boolean) {
                    if(p0?.isPressed == true){
                        viewModel.onChangeThemeMode(if(isDefaultTheme) ThemeState.NIGHT else ThemeState.DAY)
                        AppCompatDelegate.setDefaultNightMode(
                            if(isDefaultTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                        TaskStackBuilder.create(activity)
                            .addNextIntent(Intent(activity, MainActivity::class.java))
                            .addNextIntent(activity!!.intent)
                            .startActivities()
                    }
                }

            })

            appVersionCode.text = BuildConfig.VERSION_NAME

        }
    }

}