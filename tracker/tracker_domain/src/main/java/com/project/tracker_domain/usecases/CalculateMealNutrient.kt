package com.project.tracker_domain.usecases

import com.project.core.domain.model.UserInfo
import com.project.core.domain.model.sealed.ActivityLevel
import com.project.core.domain.model.sealed.Gender
import com.project.core.domain.model.sealed.GoalType
import com.project.core.util.PreferenceInterface
import com.project.tracker_domain.model.MealType
import com.project.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrient(
    private val preferenceInterface: PreferenceInterface
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {

        val allNutrients = trackedFoods.groupBy {
            it.mealType
        }
            .mapValues {
                val type = it.key
                val foods = it.value

                MealNutrients(carbs = foods.sumOf {
                    it.carbs
                },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories }, mealType = type)
            }

        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferenceInterface.getUserInfo()

        val caloryGoal = dailyCaloryRequirement(userInfo)

        val carbsGoal = (caloryGoal * userInfo.carbRatio/4f).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio/4f).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio/9f).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    //basal metabolism rate- for specific day inactivity
    private fun bmr(userInfo: UserInfo): Int {
        return when(userInfo.gender) {
            is Gender.Male -> {
                (66.47f + 13.75f * userInfo.weight +
                        5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
            }
            is Gender.Female ->  {
                (665.09f + 9.56f * userInfo.weight +
                        1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when(userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when(userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs:Int,
        val protein: Int,
        val fat:Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}