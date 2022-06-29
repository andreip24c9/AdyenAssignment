package com.adyen.android.assignment.di

import android.content.Context
import androidx.room.Room
import com.adyen.android.assignment.local.AppDatabase
import com.adyen.android.assignment.local.model.AstronomyPictureDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "PlanetaryDB"
        ).build()
    }

    @Provides
    fun provideAstronomyPictureDao(appDatabase: AppDatabase): AstronomyPictureDAO {
        return appDatabase.apodDao()
    }
}