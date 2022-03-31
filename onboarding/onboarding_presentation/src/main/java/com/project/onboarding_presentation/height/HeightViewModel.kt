package com.project.onboarding_presentation.height

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.core.domain.usecase.FilterOutDigit
import com.project.core.navigation.Route
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import com.project.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: PreferenceInterface,
    private val filterOutDigit: FilterOutDigit
): ViewModel() {

    var enteredHeight by mutableStateOf("180")
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEntered(height:String){
        if(height.length<=3) {
            enteredHeight = filterOutDigit(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = enteredHeight.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.showSnackBar(UiText.StringResource(com.project.core.R.string.error_height_cant_be_empty))
                )
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Navigate(Route.WEIGHT))
        }
    }

}