package com.project.tracker_data.mapper

import com.project.tracker_data.remote.dto.Product
import com.project.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood():TrackableFood? {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val protPer100g = nutriments.proteins100g.roundToInt()
    val fatPer100g = nutriments.fat100g.roundToInt()
    val calPer100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = productName?: return null,
        imageUrl = imageFrontThumbUrl?: return null,
        carbsPer100g = carbsPer100g,
        caloriesPer100g = calPer100g,
        proteinPer100g = protPer100g,
        fatPer100g = fatPer100g
    )
}