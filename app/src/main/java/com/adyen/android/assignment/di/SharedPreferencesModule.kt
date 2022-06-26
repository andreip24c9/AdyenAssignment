package com.adyen.android.assignment.di

import android.content.Context
import com.adyen.android.assignment.repository.SettingsRepository
import com.adyen.android.assignment.repository.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun providesSettingsRepository(@ApplicationContext context: Context) : SettingsRepository {
        return SettingsRepositoryImpl(context.getSharedPreferences("local_settings", Context.MODE_PRIVATE))
    }
}