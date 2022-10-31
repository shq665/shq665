package com.example.abcd.domain.usecase

import com.example.abcd.domain.repository.CharacterRepository
import com.example.abcd.domain.model.Character

class GetCharactersLocalUseCase(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): List<Character> {
        return characterRepository.getCharactersLocal()
    }
}