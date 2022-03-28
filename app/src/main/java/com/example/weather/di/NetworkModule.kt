package com.example.weather.di

import com.example.weather.network.ForecastApiService
import com.example.weather.network.LocationApiService
import com.example.weather.network.NetworkConstant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .baseUrl(NetworkConstant.BASE_URL)
            .build()
    }

    @Provides
    fun provideLocationApiService(retrofit: Retrofit): LocationApiService {
        return retrofit.create(LocationApiService::class.java)
    }

    @Provides
    fun provideForecastApiService(retrofit: Retrofit): ForecastApiService {
        return retrofit.create(ForecastApiService::class.java)
    }
}