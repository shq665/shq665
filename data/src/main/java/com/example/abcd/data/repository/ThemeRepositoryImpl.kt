package com.example.abcd.data.repository

import android.content.Context
import com.example.abcd.domain.model.ThemeState
import com.example.abcd.domain.repository.ThemeRepository

internal class ThemeRepositoryImpl(
    private val context: Context
) : com.example.abcd.domain.repository.ThemeRepository {

    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    override suspend fun getThemeType(): com.example.abcd.domain.model.ThemeState {
        return if ((sharedPrefs.getString(KEY_THEME_MODE, KEY_DAY_MODE)
                ?: KEY_DAY_MODE) == KEY_DAY_MODE
        )
            com.example.abcd.domain.model.ThemeState.DAY else com.example.abcd.domain.model.ThemeState.NIGHT
    }

    override suspend fun changeThemeState(thmeState: com.example.abcd.domain.model.ThemeState) {
        val saveState = if (thmeState == com.example.abcd.domain.model.ThemeState.DAY) KEY_DAY_MODE else KEY_NIGHT_MODE
        sharedPrefs.edit().putString(KEY_THEME_MODE, saveState).apply()
    }

    companion object {
        private const val PREFS_NAME = "abcd"
        private const val KEY_NIGHT_MODE = "night_mode"
        private const val KEY_DAY_MODE = "day_mode"
        private const val KEY_THEME_MODE = "theme_mode"

    }
}