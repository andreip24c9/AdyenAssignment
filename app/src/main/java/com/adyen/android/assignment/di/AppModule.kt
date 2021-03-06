package com.adyen.android.assignment.di

import android.content.Context
import com.adyen.android.assignment.presentation.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : MyApplication{
        return app as MyApplication
    }

    @Singleton
    @Provides // todo this is used just to check if @UninstallModules works properly; remove later
    fun provideSomeString() : String {
        return "It's some string"
    }

}