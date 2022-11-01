package com.example.abcd.data.di

import android.util.Log
import com.example.abcd.data.api.CharacterAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://www.breakingbadapi.com/api/"

val apiModule = module {

    single { OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build() }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create<CharacterAPI>() }
}