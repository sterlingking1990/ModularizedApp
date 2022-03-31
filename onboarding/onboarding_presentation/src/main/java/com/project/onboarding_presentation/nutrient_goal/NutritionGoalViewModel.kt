package com.project.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.core.domain.usecase.FilterOutDigit
import com.project.core.navigation.Route
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import com.project.core.util.UiText
import com.project.onboarding_domain.NutritionGoalActionValueHolder
import com.project.onboarding_domain.NutritionGoalType
import com.project.onboarding_domain.NutritionGoalTypeBusinessLogic
import com.project.onboarding_domain.NutritionGoalTypeValueHolder
import com.project.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionGoalViewModel @Inject constructor(
    private val preferenceInterface: PreferenceInterface,
    private val filterOutDigit: FilterOutDigit,
    private val nutritionGoalTypeBusinessLogic: NutritionGoalTypeBusinessLogic
):ViewModel() {

    var nutritionGoalType by mutableStateOf(NutritionGoalType())
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun processNutritionGoal(nutritionGoalValue:NutritionGoalTypeValueHolder){
        when(nutritionGoalValue){
            is NutritionGoalTypeValueHolder.CarbValue -> {
                nutritionGoalType = nutritionGoalType.copy(
                    carb = nutritionGoalValue.carb
                )
            }

            is NutritionGoalTypeValueHolder.FatValue -> {
                nutritionGoalType = nutritionGoalType.copy(
                    fat = nutritionGoalValue.fat
                )
            }

            is NutritionGoalTypeValueHolder.ProteinValue -> {
               nutritionGoalType = nutritionGoalType.copy(
                    protein = nutritionGoalValue.protein
                )
            }
            else -> Unit
        }
    }

    fun processNutritionScreenAction(nutritionGoalActionValueHolder: NutritionGoalActionValueHolder){
        when(nutritionGoalActionValueHolder){
            is NutritionGoalActionValueHolder.OnNextClick -> {
                // now we are ready to validate the values and based on success we can then
               // save the value on shared pref and then proceed else we show a snack bar
                viewModelScope.launch {
                    val result =
                        nutritionGoalTypeBusinessLogic.checkIfAllGoalSumTo100(nutritionGoalType = nutritionGoalType)

                    when (result) {

                        is NutritionGoalTypeBusinessLogic.NutritionGoalTypeBusinessLogicResultHolder.OnSuccess -> {
                            preferenceInterface.saveCarbRatio(nutritionGoalType.carb.toFloat())
                            _uiEvent.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }

                        is NutritionGoalTypeBusinessLogic.NutritionGoalTypeBusinessLogicResultHolder.OnError -> {
                            _uiEvent.send(UiEvent.showSnackBar(result.msg))
                        }
                    }

                }
            }
        }
    }

}