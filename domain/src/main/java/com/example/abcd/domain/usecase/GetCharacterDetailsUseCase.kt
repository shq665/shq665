package com.example.abcd.domain.usecase

import com.example.abcd.domain.model.Character
import com.example.abcd.domain.repository.CharacterRepository

class GetCharacterDetailsUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Character =
        characterRepository.getCharacterById(id)
}