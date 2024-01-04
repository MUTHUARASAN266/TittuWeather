package com.example.myweatherapp

import android.app.ProgressDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun getWeatherDescriptionCode(weatherCode: Int?,imageView: ImageView) {
     when (weatherCode) {
        395 -> imageView.setImageResource(WeatherIconResources.heavy_snow_with_thunder)
        392 -> imageView.setImageResource(WeatherIconResources.heavy_snow_with_thunder)
        389 -> imageView.setImageResource(WeatherIconResources.heavy_rain_with_thunder)
        386 -> imageView.setImageResource(WeatherIconResources.heavy_rain_with_thunder)
        377 -> imageView.setImageResource(WeatherIconResources.heavy_snow_with_ice)
        374 -> imageView.setImageResource(WeatherIconResources.showers_with_pellets)
        371 -> imageView.setImageResource(WeatherIconResources.heavy_snow_showers)
        368 -> imageView.setImageResource(WeatherIconResources.light_snow_showers)
        365 -> imageView.setImageResource(WeatherIconResources.heavy_sleet_showers)
        362 -> imageView.setImageResource(WeatherIconResources.light_sleet_showers)
        359 -> imageView.setImageResource(WeatherIconResources.rain_shower)
        356 -> imageView.setImageResource(WeatherIconResources.rain_shower)
        353 -> imageView.setImageResource(WeatherIconResources.Light_rain_shower)
        350 -> imageView.setImageResource(WeatherIconResources.Ice_pellets)
        338 -> imageView.setImageResource(WeatherIconResources.Heavy_snow)
        335 -> imageView.setImageResource(WeatherIconResources.Patchy_heavy_snow)
        332 -> imageView.setImageResource(WeatherIconResources.Moderate_snow)
        329 -> imageView.setImageResource(WeatherIconResources.Patchy_moderate_snow)
        326 -> imageView.setImageResource(WeatherIconResources.Light_snow)
        323 -> imageView.setImageResource(WeatherIconResources.Patchy_light_snow)
        320 -> imageView.setImageResource(WeatherIconResources.Moderate_or_heavy_sleet)
        317 -> imageView.setImageResource(WeatherIconResources.Light_sleet)
        314 -> imageView.setImageResource(WeatherIconResources.Moderate_or_Heavy_freezing_rain)
        311 -> imageView.setImageResource(WeatherIconResources.Light_freezing_rain)
        308 -> imageView.setImageResource(WeatherIconResources.Heavy_rain)
        305 -> imageView.setImageResource(WeatherIconResources.Heavy_rain_at_times)
        302 -> imageView.setImageResource(WeatherIconResources.Moderate_rain)
        299 -> imageView.setImageResource(WeatherIconResources.Moderate_rain_at_times)
        296 -> imageView.setImageResource(WeatherIconResources.Light_rain)
        293 -> imageView.setImageResource(WeatherIconResources.Patchy_light_rain)
        284 -> imageView.setImageResource(WeatherIconResources.Heavy_freezing_drizzle)
        281 -> imageView.setImageResource(WeatherIconResources.Freezing_drizzle)
        266 -> imageView.setImageResource(WeatherIconResources.Light_drizzle)
        263 -> imageView.setImageResource(WeatherIconResources.Patchy_light_drizzle)
        260 -> imageView.setImageResource(WeatherIconResources.Freezing_fog)
        248 -> imageView.setImageResource(WeatherIconResources.Fog)
        230 -> imageView.setImageResource(WeatherIconResources.Blizzard_icon)
        227 -> imageView.setImageResource(WeatherIconResources.Blowing_snow)
        200 -> imageView.setImageResource(WeatherIconResources.Thundery_outbreaks_nearby)
        185 -> imageView.setImageResource(WeatherIconResources.Patchy_freezing_drizzle_nearby)
        182 -> imageView.setImageResource(WeatherIconResources.Patchy_sleet_nearby)
        179 -> imageView.setImageResource(WeatherIconResources.Patchy_snow_nearby)
        176 -> imageView.setImageResource(WeatherIconResources.Patchy_rain_nearby)
        143 -> imageView.setImageResource(WeatherIconResources.Mist)
        122 -> imageView.setImageResource(WeatherIconResources.Overcast)
        119 -> imageView.setImageResource(WeatherIconResources.Cloudy)
        116 -> imageView.setImageResource(WeatherIconResources.Partly_Cloudy)
        113 -> imageView.setImageResource(WeatherIconResources.Clear_Sunny)
        else ->imageView.setImageResource(WeatherIconResources.SUNNY)
    }
}
fun ProgressDialog.show(title:String,message:String){
   setTitle(title)
   setMessage(message)
   show()

}
fun ProgressDialog.hide(){
   dismiss()
}
fun Context.toast(message:String){
   Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun View.snack(message: String, duration: Int){
   Snackbar.make(this,message,duration).show()

}