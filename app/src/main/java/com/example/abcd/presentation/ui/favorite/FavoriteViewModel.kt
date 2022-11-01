package com.example.abcd.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.usecase.GetCharactersLocalUseCase
import com.example.abcd.presentation.model.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class FavoriteViewModel(
    private val getCharactersLocalUseCase: GetCharactersLocalUseCase
) : ViewModel() {

     val dataFlow = MutableSharedFlow<Lce<List<Character>>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
         .onStart {
             delay(500)
             val storedList = getCharactersLocalUseCase.invoke()
             emit(Lce.Content(storedList.filter { it.isFavorite }))
        }
         .onStart { emit(Lce.Loading) }
         .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}