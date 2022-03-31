package com.project.di

import com.project.onboarding_domain.NutritionGoalTypeBusinessLogic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule{

    @Provides
    @ViewModelScoped
    fun provideNutritionGoalTypeBusinessLogic():NutritionGoalTypeBusinessLogic{
        return NutritionGoalTypeBusinessLogic()
    }
}