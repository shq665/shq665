package com.example.abcd.domain.usecase

import com.example.abcd.domain.model.ThemeState
import com.example.abcd.domain.repository.ThemeRepository

class UpdateThemeModeUseCase(
    private val themeRepository: ThemeRepository
) {

    suspend operator fun invoke(themeState: ThemeState) =
        themeRepository.changeThemeState(themeState)
}