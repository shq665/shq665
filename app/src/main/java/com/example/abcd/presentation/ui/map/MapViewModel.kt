package com.example.abcd.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.usecase.GetCharactersLocalUseCase
import com.example.abcd.presentation.model.Lce
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class MapViewModel(
    private val getCharactersLocalUseCase: com.example.abcd.domain.usecase.GetCharactersLocalUseCase
) : ViewModel() {

    private var listWithLatLng = mutableListOf<com.example.abcd.domain.model.Character>()

    private val markerFlow = MutableSharedFlow<LatLng>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = MutableSharedFlow<Lce<List<com.example.abcd.domain.model.Character>>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
        .onStart {
            delay(2000)
            val storedList = getCharactersLocalUseCase.invoke()
            listWithLatLng = storedList.toMutableList()
            emit(Lce.Content(storedList))
        }
        .onStart { emit(Lce.Loading) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    val selectedMarkerFlow = markerFlow.map { ltln ->
        listWithLatLng.filter { it.location == ltln }.first()
    }

    fun onMarkerClicked(latitude: LatLng) {
        markerFlow.tryEmit(latitude)
    }
}