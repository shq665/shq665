package com.example.abcd.data.mapper

import com.example.abcd.data.models.CharacterDTO
import com.example.abcd.data.models.CharacterEntity
import com.example.abcd.domain.model.CharacterId
import com.example.abcd.domain.model.CharacterInfo
import com.example.abcd.domain.model.Character

internal fun List<CharacterDTO>.toDomainModels(): List<Character> = map { it.toDomain() }

internal fun CharacterDTO.toDomain(): Character {
    return Character(
        id = CharacterId(id),
        name = name,
        img = img,
        characterInfo = CharacterInfo(
            nickname = nickname,
            birthday = birthday,
            status = status,
            portrayed = portrayed,
            category = category
        )
    )
}

internal fun CharacterEntity.toDomain(): Character {
    return Character(
        id = CharacterId(id),
        name = name,
        img = img,
        characterInfo = CharacterInfo(
            nickname = nickname,
            birthday = birthday,
            status = status,
            portrayed = portrayed,
            category = category
        ),
        isFavorite = isFavorite
    )
}

internal fun Character.toData(): CharacterEntity {
    return CharacterEntity(
        id = id.rawId,
        name = name,
        img = img,
        nickname = characterInfo.nickname,
        birthday = characterInfo.birthday,
        status = characterInfo.status,
        portrayed = characterInfo.portrayed,
        category = characterInfo.category,
        isFavorite = false
    )
}