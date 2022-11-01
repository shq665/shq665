package com.example.abcd.domain.usecase

import com.example.abcd.domain.model.Character
import com.example.abcd.domain.model.ThemeState
import com.example.abcd.domain.repository.ThemeRepository

class GetThemeModeUseCase(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(): ThemeState =
        themeRepository.getThemeType()
}