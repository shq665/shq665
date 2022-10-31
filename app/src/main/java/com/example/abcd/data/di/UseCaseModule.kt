package com.example.abcd.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.abcd.domain.usecase.GetCharactersLocalUseCase
import com.example.abcd.domain.usecase.GetCharactersUseCase
import com.example.abcd.domain.usecase.SaveCharactersUseCase
import com.example.abcd.domain.usecase.GetCharacterDetailsUseCase
import com.example.abcd.domain.usecase.UpdateCharacterFavoriteUseCase
import com.example.abcd.domain.usecase.GetThemeModeUseCase
import com.example.abcd.domain.usecase.UpdateThemeModeUseCase

val useCaseModule = module {
    singleOf(::GetCharactersUseCase)
    singleOf(::GetCharactersLocalUseCase)
    singleOf(::SaveCharactersUseCase)
    singleOf(::GetCharacterDetailsUseCase)
    singleOf(::UpdateCharacterFavoriteUseCase)
    singleOf(::GetThemeModeUseCase)
    singleOf(::UpdateThemeModeUseCase)
}