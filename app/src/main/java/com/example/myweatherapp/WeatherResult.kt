package com.example.myweatherapp

import com.example.myweatherapp.model.ResponseFailure
import com.example.myweatherapp.model.WeatherStackDataItem

sealed class WeatherResult{
    data class Success(val response:WeatherStackDataItem?):WeatherResult()
    data class Error(val response:WeatherStackDataItem?):WeatherResult()
    data class Loading(val isLoading: Boolean):WeatherResult()
}
