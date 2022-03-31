package com.project.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.core.domain.model.sealed.GoalType
import com.project.core.navigation.Route
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GoalViewModel @Inject constructor(
    private val preferenceInterface: PreferenceInterface
):ViewModel() {

    var savedGoal by mutableStateOf<GoalType>(GoalType.LoseWeight)
    private set

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelected(goalSelected:GoalType){
        savedGoal = goalSelected
    }

    fun onNextClick(){
        viewModelScope.launch {
            preferenceInterface.saveGoalType(savedGoal)
            _uiEvent.send(UiEvent.Navigate(Route.NUTRIENT_GOAL))
        }
    }
}