package com.example.abcd.data.di


import com.example.abcd.domain.repository.CharacterRepository
import com.example.abcd.data.repository.CharacterRepositoryImpl
import com.example.abcd.data.repository.ThemeRepositoryImpl
import com.example.abcd.domain.repository.ThemeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::CharacterRepositoryImpl) {
        bind<CharacterRepository>()
    }
    singleOf(::ThemeRepositoryImpl) {
        bind<ThemeRepository>()
    }
}