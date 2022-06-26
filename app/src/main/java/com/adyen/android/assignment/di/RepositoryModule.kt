package com.adyen.android.assignment.di

import android.content.SharedPreferences
import com.adyen.android.assignment.network.ApiService
import com.adyen.android.assignment.network.model.AstronomyPictureDTOMapper
import com.adyen.android.assignment.repository.PlanetaryRepository
import com.adyen.android.assignment.repository.PlanetaryRepositoryImpl
import com.adyen.android.assignment.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesPlanetaryRepository(
        apiService: ApiService,
        mapper: AstronomyPictureDTOMapper
    ) : PlanetaryRepository {
        return PlanetaryRepositoryImpl(apiService, mapper)
    }

//    @Singleton
//    @Provides
//    fun providesSettingsRepository(
//        sharedPreferences: SharedPreferences
//    ) : SettingsRepositoryImpl {
//        return SettingsRepositoryImpl(sharedPreferences)
//    }
}