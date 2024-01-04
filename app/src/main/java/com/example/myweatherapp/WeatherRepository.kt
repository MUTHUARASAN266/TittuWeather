// WeatherRepository.kt

package com.example.myweatherapp

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WeatherRepository @Inject constructor(private val api: WeatherStackApi) {

    private val TAG = "WeatherRepository"

    suspend fun myWeather(apiKey: String, location: String): WeatherResult {
        val response = api.getCurrentWeather1(apiKey, location)
        return try {
            if (response.isSuccessful) {
                if (response.body()?.error != null) {
                    WeatherResult.Error(response.body())
                } else {
                    val responseBody = response.body()
                    WeatherResult.Success(responseBody)

                }
            } else {
                val errorBody = response.errorBody().toString()
                Log.e(TAG, "myWeather: $errorBody")
                WeatherResult.Error(response.body())
            }
        } catch (e: Exception) {
            Log.e(TAG, "myWeather: $e")
            WeatherResult.Error(response.body())
        }
    }
}
