package com.project.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.core.domain.model.sealed.ActivityLevel
import com.project.core.navigation.Route
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val preferences: PreferenceInterface
):ViewModel() {

    var selectedActivity by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
    private set

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onActivitySelected(activity:ActivityLevel){
        selectedActivity = activity
    }

    fun onNextClick(){
        viewModelScope.launch {
           preferences.saveActivityLevel(selectedActivity)
            _uiEvent.send(UiEvent.Navigate(Route.GOAL))
        }
    }



}