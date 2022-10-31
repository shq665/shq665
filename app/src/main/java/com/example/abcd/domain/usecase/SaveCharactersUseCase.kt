package com.example.abcd.domain.usecase

import com.example.abcd.domain.repository.CharacterRepository
import com.example.abcd.domain.model.Character

class SaveCharactersUseCase(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(characters: List<Character>) {
        characterRepository.saveCharacters(characters)
    }
}