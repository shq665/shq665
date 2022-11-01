package com.example.abcd.data.models

import com.google.gson.annotations.SerializedName

internal data class CharacterDTO(
    @SerializedName("char_id")
    val id: Int,
    val name: String,
    val img: String,
    val nickname: String,
    val birthday: String,
    val status: String,
    val portrayed: String,
    val category: String
)