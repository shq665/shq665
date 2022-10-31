package com.example.abcd.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.abcd.data.models.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao
}