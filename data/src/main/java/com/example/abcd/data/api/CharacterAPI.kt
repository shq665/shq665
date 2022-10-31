package com.example.abcd.data.api

import com.example.abcd.data.models.CharacterDTO
import com.example.abcd.data.models.CharacterEntity
import com.example.abcd.data.models.CharacterResponseDTO
import com.example.abcd.domain.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

internal interface CharacterAPI {

    @GET("characters")
    suspend fun getCharacters(): List<CharacterDTO>

    @GET("characters/{id}")
    suspend fun getDetails(
        @Path("id") id: Int
    ): List<CharacterDTO>

}