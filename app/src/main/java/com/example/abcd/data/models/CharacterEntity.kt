package com.example.abcd.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "char_id")
    val id: Int,
    val name: String,
    val img: String,
    val nickname: String,
    val birthday: String,
    val status: String,
    val portrayed: String,
    val category: String,
    val isFavorite: Boolean
)