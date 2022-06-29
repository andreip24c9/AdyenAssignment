package com.adyen.android.assignment.di

import com.adyen.android.assignment.local.model.AstronomyPictureDAO
import com.adyen.android.assignment.network.ApiService
import com.adyen.android.assignment.network.model.AstronomyPictureDTOMapper
import com.adyen.android.assignment.repository.PlanetaryRepository
import com.adyen.android.assignment.repository.PlanetaryRepositoryImpl
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
        mapper: AstronomyPictureDTOMapper,
        dao: AstronomyPictureDAO
    ) : PlanetaryRepository {
        return PlanetaryRepositoryImpl(apiService, mapper, dao)
    }
}