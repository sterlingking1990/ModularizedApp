package com.project.core.domain.model

import com.project.core.domain.model.sealed.ActivityLevel
import com.project.core.domain.model.sealed.Gender
import com.project.core.domain.model.sealed.GoalType

data class UserInfo(
    val gender: Gender,
    val age: Int,
    val weight: Float,
    val height: Int,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float
)
