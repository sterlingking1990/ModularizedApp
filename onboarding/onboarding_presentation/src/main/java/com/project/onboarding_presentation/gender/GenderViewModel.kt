package com.project.onboarding_presentation.gender

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.core.domain.model.sealed.Gender
import com.project.core.navigation.Route
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preferences: PreferenceInterface
): ViewModel(){

    var selectedGender by mutableStateOf<Gender>(Gender.Male)
    private set

    //set up a channel for navigation, announce trigger states, etc

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender){
        selectedGender = gender
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvent.send(UiEvent.Navigate(Route.AGE))
        }
    }

}