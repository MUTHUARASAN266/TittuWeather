package com.example.myweatherapp.model


import com.google.gson.annotations.SerializedName

data class WeatherStackDataItem(
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("current")
    val current: Current?,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("request")
    val request: Request?
)