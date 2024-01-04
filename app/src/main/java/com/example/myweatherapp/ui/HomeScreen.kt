// HomeScreen.kt

package com.example.myweatherapp.ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myweatherapp.MyApp
import com.example.myweatherapp.R
import com.example.myweatherapp.ThemePreferencesManager
import com.example.myweatherapp.WeatherResult
import com.example.myweatherapp.WeatherViewModel
import com.example.myweatherapp.databinding.ActivityHomeScreenBinding
import com.example.myweatherapp.getWeatherDescriptionCode
import com.example.myweatherapp.show
import com.example.myweatherapp.snack
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val TAG = "HomeScreen"
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this@HomeScreen)
        binding.run {
            weatherResponse.speed = 0.3f
            weatherResponse.playAnimation()
            btnFind.setOnClickListener {
                validation(locationEdt.text.toString().trim())
            }
        }
        getMuhtu(binding.themeBtn.isChecked)
        // Check if the switch value exists
//        if (ThemePreferencesManager.containsLightMode(ThemePreferencesManager.getSharedPreferences(this@HomeScreen))) {
//            val isLightMode = ThemePreferencesManager.getThemeValue(this)
//            binding.themeBtn.isChecked = isLightMode
//        }

        binding.themeBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            onThemeSwitchChanged(isChecked)
          //  themeDatastore(buttonView.isChecked)
            storeMuthu(binding.themeBtn.isChecked)
        }
    }

    private fun getMuhtu(checked: Boolean) {
        ThemePreferencesManager.muthuGet(this)
    }

    private fun storeMuthu(checked: Boolean) {
        ThemePreferencesManager.muthuStore(this,checked)
    }

    private fun themeDatastore(checked: Boolean) {
        Log.e(TAG, "themeDatastore: $checked")
        val store=ThemePreferencesManager.storeThemeValue(this@HomeScreen,checked)
        Log.e(TAG, "store: $store")
    }

    private fun onThemeSwitchChanged(checked: Boolean) {
        ThemePreferencesManager.setTheme(this, checked)
        (application as MyApp).setAppTheme(checked)
       // recreate() // Recreate the activity to apply the new theme

    }

    private fun validation(string: String) {
        if (string.isEmpty()) {
            Snackbar.make(binding.root, "Please enter location", Snackbar.LENGTH_SHORT).show()
        } else {
            getCurrentWeather(string)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentWeather(location: String) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.weatherData(API_KEY, location).observe(this@HomeScreen, Observer { result ->
                when (result) {
                    is WeatherResult.Success -> {
                        // Handle the weather response
                        val cityName = result.response?.location?.name
                        val weatherCode = result.response?.current?.weatherCode
                        val temperature = result.response?.current?.temperature
                        val weatherDescriptions = result.response?.current?.weatherDescriptions?.get(0)
                        // Log something with the weather data
                        val degreeSymbol = "\u00b0"
                        Log.e(TAG, "City: $cityName")
                        Log.e(TAG, "Weather: $weatherDescriptions")
                        Log.e(TAG, "Temperature: $temperature$degreeSymbol")
                        binding.apply {
                            showWeatherDetails()
                            binding.cityName.text = result.response?.location?.name
                            weatherName.text = result.response?.current?.weatherDescriptions?.get(0)
                            rainTemp.text = "${result.response?.current?.temperature}$degreeSymbol"
                            getWeatherDescriptionCode(weatherCode, binding.weatherIcon)
                        }
                    }

                    is WeatherResult.Error -> {
                        showLoading()
                        binding.root.snack(result.response?.error?.info.toString(),Snackbar.LENGTH_SHORT)
                        Log.e(TAG, "getCurrentWeather: error - ${result.response?.error?.info}")

                    }

                    is WeatherResult.Loading -> {
                        showLoading(result)
                    }
                }
            })

        }
    }

    private fun showLoading(loading: WeatherResult.Loading) {
        if (loading.isLoading) {
            progressDialog.show(resources.getString(R.string.app_name), "getting weather report..")

        } else {
            progressDialog.hide()
        }
    }
    private fun showWeatherDetails() {
        binding.apply {
            cityName.visibility = View.VISIBLE
            weatherIcon.visibility = View.VISIBLE
            weatherName.visibility = View.VISIBLE
            rainTemp.visibility = View.VISIBLE
            weatherResponse.visibility = View.GONE
        }
    }
    private fun showLoading() {
        binding.apply {
            cityName.visibility = View.GONE
            weatherIcon.visibility = View.GONE
            weatherName.visibility = View.GONE
            rainTemp.visibility = View.GONE
            weatherResponse.visibility = View.VISIBLE
            weatherResponse.setAnimation(R.raw.animation02)
            weatherResponse.speed = 0.3f
            weatherResponse.playAnimation()
        }
    }
    companion object {
        private const val API_KEY = "62c7a6ddc90bee473661c245d36d239e"
    }
}


