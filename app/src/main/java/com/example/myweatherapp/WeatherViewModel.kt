// WeatherViewModel.kt

package com.example.myweatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val TAG = "WeatherViewModel"

    suspend fun weatherData(apiKey: String, location: String) = liveData(Dispatchers.IO) {

        emit(WeatherResult.Loading(true))
        try {
            val result = weatherRepository.myWeather(apiKey, location)
            emit(result)
        } catch (e: Exception) {
            emit(WeatherResult.Loading(false))
            Log.e(TAG, "weatherData: ${e.message}")
            emit(WeatherResult.Error(null))
        }finally {
            emit(WeatherResult.Loading(false))

        }
    }
}
