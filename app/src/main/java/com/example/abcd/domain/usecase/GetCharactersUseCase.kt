package com.example.abcd.domain.usecase

import com.example.abcd.domain.repository.CharacterRepository
import com.example.abcd.domain.model.Character

class GetCharactersUseCase(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(): Result<List<Character>> =
        characterRepository.getCharactersRemote()
}