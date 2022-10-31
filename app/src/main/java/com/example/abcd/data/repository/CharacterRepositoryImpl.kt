package com.example.abcd.data.repository

import com.example.abcd.data.api.CharacterAPI
import com.example.abcd.data.database.CharacterDao
import com.example.abcd.data.mapper.toData
import com.example.abcd.data.mapper.toDomain
import com.example.abcd.data.mapper.toDomainModels
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val bbApi: CharacterAPI,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharactersRemote(): Result<List<Character>> {
        return runCatching {
            bbApi.getCharacters().toDomainModels()
        }
    }

    override suspend fun getCharactersLocal(): List<Character> {
        return characterDao.getCharacters().map { it.toDomain() }
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        characterDao.insertCharacters(characters.map { it.toData() })
    }

    override suspend fun getCharacterById(id: Int): Character {
        return characterDao.getCharacterById(id).toDomain()
    }

    override suspend fun updateFavoriteCharacter(id: Int, isFavorite: Boolean): Boolean {
        characterDao.updateFavorite(id, isFavorite)
        return isFavorite
    }

}