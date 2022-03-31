package com.project.tracker_domain.usecases

import com.project.tracker_domain.repository.TrackerRepository

data class TrackerUseCases(
    val searchFood: SearchFood,
    val trackFood: TrackFood,
    val getFoodsForDate: GetFoodsForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrient: CalculateMealNutrient)
