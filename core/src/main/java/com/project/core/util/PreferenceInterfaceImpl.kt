package com.project.core.util

import android.content.SharedPreferences
import com.project.core.domain.model.UserInfo
import com.project.core.domain.model.sealed.ActivityLevel
import com.project.core.domain.model.sealed.Gender
import com.project.core.domain.model.sealed.GoalType

class PreferenceInterfaceImpl(
    val sharedPref:SharedPreferences
):PreferenceInterface {
    override fun saveGender(gender: Gender) {

        sharedPref.edit()
            .putString(PreferenceInterface.KEY_GENDER,gender.gender)
            .apply()
    }

    override fun saveAge(age: Int) {

        sharedPref.edit()
            .putInt(PreferenceInterface.KEY_AGE,age)
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(PreferenceInterface.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(PreferenceInterface.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit()
            .putString(PreferenceInterface.KEY_ACTIVITY_LEVEL, level.activityLevel)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPref.edit()
            .putString(PreferenceInterface.KEY_GOAL_TYPE, type.goalType)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(PreferenceInterface.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(PreferenceInterface.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(PreferenceInterface.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun getUserInfo(): UserInfo {

        val age = sharedPref.getInt(PreferenceInterface.KEY_AGE, -1)
        val height = sharedPref.getInt(PreferenceInterface.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(PreferenceInterface.KEY_WEIGHT, -1f)
        val genderString = sharedPref.getString(PreferenceInterface.KEY_GENDER, null)
        val activityLevelString = sharedPref
            .getString(PreferenceInterface.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPref.getString(PreferenceInterface.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(PreferenceInterface.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(PreferenceInterface.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(PreferenceInterface.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(genderString ?: "male"),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
            goalType = GoalType.fromString(goalType ?: "keep_weight"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(PreferenceInterface.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnboarding(): Boolean {
        return sharedPref.getBoolean(
            PreferenceInterface.KEY_SHOULD_SHOW_ONBOARDING,
            true
        )
    }

}