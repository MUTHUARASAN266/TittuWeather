// AppModule.kt

package com.example.myweatherapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {
    @Provides
    @Singleton
    fun provideWeatherStackApi(): WeatherStackApi {
        return Retrofit.Builder()
            .baseUrl("http://api.weatherstack.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherStackApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherStackApi): WeatherRepository {
        return WeatherRepository(api)
    }
}
