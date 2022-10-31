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
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val updateThemeModeUseCase: UpdateThemeModeUseCase
) : ViewModel() {

    val themeFlow = flow {
        emit(getThemeModeUseCase.invoke())
    }

    val themeModeFlow = MutableSharedFlow<ThemeState>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
        .onStart {
            //getThemeModeUseCase.invoke()
            ThemeState.NIGHT
        }
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)


    fun onChangeThemeMode(themeState: ThemeState){
        viewModelScope.launch { updateThemeModeUseCase.invoke(themeState) }
    }


}