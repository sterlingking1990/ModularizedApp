package com.project.onboarding_domain

sealed class NutritionGoalTypeValueHolder{
    data class ProteinValue(val protein:String):NutritionGoalTypeValueHolder()
    data class CarbValue(val carb:String): NutritionGoalTypeValueHolder()
    data class FatValue(val fat:String): NutritionGoalTypeValueHolder()
}
