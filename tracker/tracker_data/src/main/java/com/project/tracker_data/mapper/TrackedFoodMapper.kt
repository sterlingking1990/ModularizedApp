package com.project.tracker_data.mapper

import com.project.tracker_data.local.TrackerFoodEntity
import com.project.tracker_domain.model.MealType
import com.project.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFood.toTrackedFoodEntity(): TrackerFoodEntity {
    return TrackerFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories,
        id = id
    )
}

fun TrackerFoodEntity.toTrackedFood():TrackedFood{
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year,month,dayOfMonth),
        calories = calories,
        id = id
    )
}