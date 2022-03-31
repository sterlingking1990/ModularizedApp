package com.project.tracker_domain.di

import com.project.core.util.PreferenceInterface
import com.project.tracker_domain.repository.TrackerRepository
import com.project.tracker_domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideUseCases(repository: TrackerRepository, preferenceInterface: PreferenceInterface): TrackerUseCases{
        return TrackerUseCases(
            SearchFood(repository), TrackFood(repository), GetFoodsForDate(repository),
            DeleteTrackedFood(repository),CalculateMealNutrient(preferenceInterface)
        )
    }
}