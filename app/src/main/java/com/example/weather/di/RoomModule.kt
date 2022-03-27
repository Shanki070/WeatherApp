package com.example.weather.di

import android.content.Context
import com.example.weather.room.AppDatabase
import com.example.weather.room.ForecastDao
import com.example.weather.room.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }

    @Provides
    fun provideScanAndPartAndUploadDao(appDatabase: AppDatabase): ForecastDao {
        return appDatabase.forecastDao()
    }
}