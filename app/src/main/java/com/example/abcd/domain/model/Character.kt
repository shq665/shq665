package com.example.abcd.domain.model

import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

@JvmInline
value class CharacterId(val rawId: Int)

data class CharacterInfo(
    val nickname: String,
    val birthday: String,
    val status: String,
    val portrayed: String,
    val category: String
)

data class Character(
    val id: CharacterId,
    val name: String,
    val img: String,
    val characterInfo: CharacterInfo,
    val isFavorite: Boolean = false,
    val location: LatLng = LatLng(
        randomLat(), randomLng()
    )
)

fun randomLat(): Double = Random.nextDouble(52.0, 55.0)
fun randomLng(): Double = Random.nextDouble(26.0, 28.0)