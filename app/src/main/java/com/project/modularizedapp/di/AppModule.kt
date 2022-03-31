package com.project.modularizedapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.project.core.domain.usecase.FilterOutDigit
import com.project.core.util.PreferenceInterface
import com.project.core.util.PreferenceInterfaceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSharedPreference(application: Application):SharedPreferences{
        return application.getSharedPreferences("shared_pref",MODE_PRIVATE)
    }

    @Provides
    fun providePreferenceInterface(sharedPreferences: SharedPreferences):PreferenceInterface{
        return PreferenceInterfaceImpl(sharedPref = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFilterOutDigitUseCase(): FilterOutDigit{
        return FilterOutDigit()
    }

}