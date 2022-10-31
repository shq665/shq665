package com.example.abcd.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.usecase.GetCharacterDetailsUseCase
import com.example.abcd.domain.usecase.UpdateCharacterFavoriteUseCase
import com.example.abcd.presentation.model.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val updateCharacterFavoriteUseCase: UpdateCharacterFavoriteUseCase,
    private val id: Int
) : ViewModel() {

    private var isFavoriteCharacter = false

    private val updateFavoriteIdFlow = MutableSharedFlow<Int>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val detailsCharacterFlow = MutableSharedFlow<Lce<Character>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
        .onStart {
            delay(2000)
            val character = getCharacterDetailsUseCase.invoke(id)
            isFavoriteCharacter = character.isFavorite
            emit(Lce.Content(character))
        }
        .onStart { emit(Lce.Loading) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    val updateFavoriteFlow = updateFavoriteIdFlow.map { id ->
        isFavoriteCharacter = !isFavoriteCharacter
        updateCharacterFavoriteUseCase.invoke(id, isFavoriteCharacter)
        isFavoriteCharacter
    }

    fun onUpdateClicked(id: Int) {
        updateFavoriteIdFlow.tryEmit(id)
    }
}