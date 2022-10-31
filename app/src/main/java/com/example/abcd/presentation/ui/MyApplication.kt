package com.example.abcd.presentation.ui

import android.app.Application
import com.example.abcd.data.di.apiModule
import com.example.abcd.data.di.databaseModule
import com.example.abcd.data.di.repositoryModule
import com.example.abcd.data.di.useCaseModule
import com.example.abcd.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                apiModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}