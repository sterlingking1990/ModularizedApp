package com.project.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.core.util.PreferenceInterface
import com.project.core.util.UiEvent
import com.project.tracker_domain.repository.TrackerRepository
import com.project.tracker_domain.usecases.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferenceInterface: PreferenceInterface,
    private val useCases: TrackerUseCases
):ViewModel() {

    var trackerState by mutableStateOf<TrackerOverviewState>(TrackerOverviewState())
    private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var refreshFoodJob:Job? = null

    init {
        refreshFood()
        preferenceInterface.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent){
        when(event){
            is TrackerOverviewEvent.OnNextDayClick -> {
                trackerState = trackerState.copy(
                    date =trackerState.date.plusDays(1)
                )
                refreshFood()
            }

            is TrackerOverviewEvent.OnPreviousDayClick -> {
                trackerState = trackerState.copy(
                    date = trackerState.date.minusDays(1)
                )
                refreshFood()
            }

            is TrackerOverviewEvent.OnDeleteTrackFoodClicked -> {
                viewModelScope.launch {
                    useCases.deleteTrackedFood(event.trackedFood)
                }
                refreshFood()
            }

            is TrackerOverviewEvent.OnToggleMealClick -> {
                trackerState = trackerState.copy(
                    meals = trackerState.meals.map {
                        if(it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        }else it
                    }
                )
            }
        }
    }

    private fun refreshFood(){
        refreshFoodJob?.cancel()
        refreshFoodJob = useCases.getFoodsForDate(
            trackerState.date).onEach {
                foods ->
            val nutrients = useCases.calculateMealNutrient(foods)
            trackerState = trackerState.copy(
                totalCarbs = nutrients.totalCarbs,
                totalProtein = nutrients.totalProtein,
                totalFat = nutrients.totalFat,
                totalCalories = nutrients.totalCalories,
                carbsGoal = nutrients.carbsGoal,
                proteinGoal = nutrients.proteinGoal,
                fatGoal = nutrients.fatGoal,
                caloriesGoal = nutrients.caloriesGoal,
                trackedFoods = foods,
                meals = trackerState.meals.map {
                    val nutrientForMeal = nutrients.mealNutrients[it.mealType]
                        ?: return@map it.copy(
                            carbs = 0,
                            protein = 0,
                            fat = 0,
                            calories = 0
                        )
                    it.copy(
                        carbs = nutrientForMeal.carbs,
                        protein = nutrientForMeal.protein,
                        fat = nutrientForMeal.fat,
                        calories = nutrientForMeal.calories
                    )
                }
            )
        }
            .launchIn(viewModelScope)
    }
}