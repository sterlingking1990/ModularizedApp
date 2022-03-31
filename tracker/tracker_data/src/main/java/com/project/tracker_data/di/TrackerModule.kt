package com.project.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.project.tracker_data.local.TrackerFoodDBService
import com.project.tracker_data.remote.FoodAPI
import com.project.tracker_data.remote.TrackerRetrofit
import com.project.tracker_data.repository.TrackerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrackerModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return TrackerRetrofit().createService()
    }

    @Provides
    @Singleton
    fun providesFoodApi(retrofit: Retrofit):FoodAPI{
        return retrofit.create(FoodAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTrackerDbService(app:Application):TrackerFoodDBService{
        return Room.databaseBuilder(app,TrackerFoodDBService::class.java,"tracker-db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTrackableRepository(
        trackerFoodDBService: TrackerFoodDBService,foodAPI: FoodAPI
    ):TrackerRepositoryImpl{
        return TrackerRepositoryImpl(foodAPI,trackerFoodDBService.trackerAccessObject)
    }

}