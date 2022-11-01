package com.example.abcd.domain.usecase

import com.example.abcd.domain.model.Character
import com.example.abcd.domain.repository.CharacterRepository

class UpdateCharacterFavoriteUseCase(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        characterRepository.updateFavoriteCharacter(id, isFavorite)
    }
}