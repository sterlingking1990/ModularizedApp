package com.project.core.domain.model.sealed

sealed class Gender(val gender:String){
    object Male: Gender("male")
    object Female: Gender("female")

    companion object {
        fun fromString(gender: String): Gender {
            return when (gender) {
                "male" -> Male
                "female" -> Female
                else -> Female
            }
        }
    }
}
