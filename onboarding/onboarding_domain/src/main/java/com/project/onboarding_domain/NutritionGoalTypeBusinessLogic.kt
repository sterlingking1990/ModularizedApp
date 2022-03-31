package com.project.onboarding_domain

import com.project.core.util.UiText

class NutritionGoalTypeBusinessLogic {

    fun checkIfAllGoalSumTo100(nutritionGoalType: NutritionGoalType): NutritionGoalTypeBusinessLogicResultHolder {
        return if (nutritionGoalType.carb.isEmpty() || nutritionGoalType.protein.isEmpty() || nutritionGoalType.fat.isEmpty()) {
            NutritionGoalTypeBusinessLogicResultHolder.OnError(UiText.StringResource(com.project.core.R.string.error_invalid_values))
        } else if (nutritionGoalType.carb.toInt() + nutritionGoalType.fat.toInt() + nutritionGoalType.protein.toInt() < 100) {
            NutritionGoalTypeBusinessLogicResultHolder.OnError(UiText.StringResource(com.project.core.R.string.error_not_100_percent))
        } else if (nutritionGoalType.carb.toInt() + nutritionGoalType.fat.toInt() + nutritionGoalType.protein.toInt() > 100) {
            NutritionGoalTypeBusinessLogicResultHolder.OnError(UiText.StringResource(com.project.core.R.string.error_not_100_percent))
        } else {
            NutritionGoalTypeBusinessLogicResultHolder.OnSuccess(
                nutritionGoalType.protein.toFloat() / 100,
                nutritionGoalType.carb.toFloat() / 100,
                nutritionGoalType.fat.toFloat() / 100
            )
        }
    }

    //set up business logic result holder

    sealed class NutritionGoalTypeBusinessLogicResultHolder{
        data class OnSuccess(val prot:Float, val carb:Float, val fat:Float):NutritionGoalTypeBusinessLogicResultHolder()
        data class OnError(val msg: UiText):NutritionGoalTypeBusinessLogicResultHolder()
    }


}