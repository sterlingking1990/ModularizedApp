package com.project.onboarding_presentation.age

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
class AgeViewModel @Inject constructor(
    private val preferences: PreferenceInterface,
    private val filterOutDigit: FilterOutDigit
):ViewModel() {

    var enteredAge by mutableStateOf("20")
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEntered(age:String){
        if(age.length<=3) {
            enteredAge = filterOutDigit(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = enteredAge.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(
                    UiEvent.showSnackBar(UiText.StringResource(com.project.core.R.string.error_age_cant_be_empty))
                )
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Navigate(Route.HEIGHT))
        }
    }

}