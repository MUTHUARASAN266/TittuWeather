package com.example.myweatherapp.model


import com.google.gson.annotations.SerializedName

data class ResponseFailure(
    @SerializedName("error")
    val error: Error,
    @SerializedName("success")
    val success: Boolean
)