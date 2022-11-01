package com.example.abcd.domain.repository

import com.example.abcd.domain.model.ThemeState

interface ThemeRepository {
    suspend fun getThemeType(): ThemeState
    suspend fun changeThemeState(thmeState: ThemeState)
}