package com.project.tracker_domain.usecases

import com.project.tracker_domain.model.MealType
import com.project.tracker_domain.model.TrackableFood
import com.project.tracker_domain.model.TrackedFood
import com.project.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date:LocalDate
    ){

        repository.insertTrackedFood(
            TrackedFood(
                food.name,
                ((food.carbsPer100g /100f)* amount).roundToInt(),
                ((food.proteinPer100g/ 100f)*amount).roundToInt(),
                ((food.fatPer100g/100f)*amount).roundToInt(),
                food.imageUrl,
                mealType,
                amount,
                date,
                ((food.caloriesPer100g/100f)*amount).roundToInt()
        )
        )
    }
}