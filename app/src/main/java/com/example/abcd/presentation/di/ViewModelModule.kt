package com.example.abcd.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.abcd.presentation.ui.characters.CharactersViewModel
import com.example.abcd.presentation.ui.details.DetailsViewModel
import com.example.abcd.presentation.ui.favorite.FavoriteViewModel
import com.example.abcd.presentation.ui.settings.SettingsViewModel
import com.example.abcd.presentation.ui.map.MapViewModel

val viewModelModule = module {
    viewModelOf(::CharactersViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::MapViewModel)

}