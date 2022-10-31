package com.example.abcd.presentation.ui.characters


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.usecase.GetCharactersLocalUseCase
import com.example.abcd.domain.usecase.GetCharactersUseCase
import com.example.abcd.domain.usecase.SaveCharactersUseCase
import com.example.abcd.presentation.model.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersLocalUseCase: GetCharactersLocalUseCase,
    private val saveCharactersUseCase: SaveCharactersUseCase
) : ViewModel() {


    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val dataFlow = refreshFlow.map {
        delay(2000)
        runCatching { getCharactersUseCase.invoke() }
            .onSuccess { Lce.Content(it) }
            .onFailure { Lce.Error(it) }
    }.map {result ->
        result.map {
            it.fold(
                onSuccess = {
                    saveCharactersUseCase.invoke(it)
                    Lce.Content(it)
                }, onFailure = {Lce.Error(it)}
            )
        }
    }.onStart {
        val storedList = getCharactersLocalUseCase.invoke()
        val state = if (storedList.isNotEmpty()) {
            Lce.Content(storedList)
        } else {
            Lce.Loading
        }
        emit(Result.success(state))
    }.shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    fun onRefreshed() {
        refreshFlow.tryEmit(Unit)
    }

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    init {
        onRefreshed()
    }


}