package com.example.abcd.domain.repository

import com.example.abcd.domain.model.Character

interface CharacterRepository {

    suspend fun getCharactersRemote(): Result<List<Character>>

    suspend fun getCharactersLocal(): List<Character>

    suspend fun saveCharacters(characters: List<Character>)

    suspend fun getCharacterById(id: Int): Character

    suspend fun updateFavoriteCharacter(id: Int, isFavorite: Boolean): Boolean
}