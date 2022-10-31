package com.example.abcd.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abcd.data.models.CharacterEntity

@Dao
internal interface CharacterDao {

    @Query("SELECT * FROM  CharacterEntity")
    suspend fun getCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM  CharacterEntity WHERE char_id =:id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("UPDATE CharacterEntity SET isFavorite=:isFavorite WHERE char_id =:id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM CharacterEntity")
    suspend fun clear()
}