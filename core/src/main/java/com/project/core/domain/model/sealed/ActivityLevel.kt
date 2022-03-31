package com.project.core.domain.model.sealed

sealed class ActivityLevel(val activityLevel:String){
    object Low: ActivityLevel("low")
    object Medium: ActivityLevel("medium")
    object High: ActivityLevel("high")

    companion object {
        fun fromString(activityLevel:String):ActivityLevel{
            return when(activityLevel){
                "low" -> Low
                "medium" -> Medium
                "high" -> High
                else -> Medium
            }
        }
    }
}
