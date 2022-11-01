package com.example.abcd.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abcd.domain.model.Character
import com.example.abcd.domain.model.ThemeState
import com.example.abcd.domain.usecase.GetThemeModeUseCase
import com.example.abcd.domain.usecase.UpdateThemeModeUseCase
import com.example.abcd.presentation.model.Lce
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getThemeModeUseCase: com.example.abcd.domain.usecase.GetThemeModeUseCase,
    private val updateThemeModeUseCase: com.example.abcd.domain.usecase.UpdateThemeModeUseCase
) : ViewModel() {

    val themeFlow = flow {
        emit(getThemeModeUseCase.invoke())
    }

    val themeModeFlow = MutableSharedFlow<com.example.abcd.domain.model.ThemeState>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
        .onStart {
            //getThemeModeUseCase.invoke()
            com.example.abcd.domain.model.ThemeState.NIGHT
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)


    fun onChangeThemeMode(themeState: com.example.abcd.domain.model.ThemeState){
        viewModelScope.launch { updateThemeModeUseCase.invoke(themeState) }
    }


}