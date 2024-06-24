package com.example.myweatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myweatherapp.databinding.FragmentHomeScreenBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var progressAlertDialog: AlertDialog
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: $savedInstanceState")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater)
        Log.e(TAG, "onCreateView: $savedInstanceState")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated: $savedInstanceState")
        binding.run {
            weatherResponse.speed = 0.3f
            weatherResponse.playAnimation()
            btnFind.setOnClickListener {
                validation(locationEdt.text.toString().trim())
            }
        }
    }

    private fun validation(string: String) {
        if (string.isEmpty()) {
            Snackbar.make(binding.root, "Please enter location", Snackbar.LENGTH_SHORT).show()
        } else {
            getCurrentWeather(string)
            Log.e(TAG, "validation: $string+1")
            BuildConfig.API_KEY
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentWeather(location: String) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.weatherData(BuildConfig.API_KEY, location.trim())
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is WeatherResult.Success -> {
                            // Handle the weather response
                            val cityName = result.response?.location?.name
                            val weatherCode = result.response?.current?.weatherCode
                            val temperature = result.response?.current?.temperature
                            val weatherDescriptions = result.response?.current?.weatherDescriptions?.get(0)
                            val windSpeed = result.response?.current?.windSpeed
                            val humidity = result.response?.current?.humidity
                            // Log something with the weather data
                            val degreeSymbol = "\u00b0"
                            Log.e(TAG, "City: $cityName")
                            Log.e(TAG, "Weather: $weatherDescriptions")
                            Log.e(TAG, "Temperature: $temperature$degreeSymbol")
                            Log.e(TAG, "windSpeed: $windSpeed")
                            Log.e(TAG, "humidity: $humidity")
                            binding.apply {
                                showWeatherDetails()
                                binding.cityName.text = result.response?.location?.name
                                weatherName.text = result.response?.current?.weatherDescriptions?.get(0)
                                rainTemp.text = "${result.response?.current?.temperature}$degreeSymbol"
                                windText.text = "WindSpeed\n${windSpeed.toString()} km/h"
                                humidityText.text = "Humidity\n${humidity.toString()}%"
                                getWeatherDescriptionCode(weatherCode, binding.weatherIcon)
                            }
                        }

                        is WeatherResult.Error -> {
                            showLoading()
                            binding.root.snack(
                                result.response?.error?.info.toString(),
                                Snackbar.LENGTH_SHORT
                            )
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
            progressAlertDialog = showCustomProgressDialog(
                requireContext(),
                resources.getString(R.string.app_name),
                "getting weather report.."
            )
        } else {
            hideCustomProgressDialog(progressAlertDialog)
        }
    }

    private fun showWeatherDetails() {
        binding.apply {
            cityName.visibility = View.VISIBLE
            weatherIcon.visibility = View.VISIBLE
            weatherName.visibility = View.VISIBLE
            rainTemp.visibility = View.VISIBLE
            weatherResponse.visibility = View.GONE
            weatherDetailsCardView.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.apply {
            cityName.visibility = View.GONE
            weatherIcon.visibility = View.GONE
            weatherName.visibility = View.GONE
            rainTemp.visibility = View.GONE
            weatherDetailsCardView.visibility = View.GONE
            weatherResponse.visibility = View.VISIBLE
            weatherResponse.setAnimation(R.raw.animation02)
            weatherResponse.speed = 0.3f
            weatherResponse.playAnimation()
        }
    }

    companion object {
        private const val API_KEY = "d9937744995ec572e4fd154b182ff4c3"
        private const val TAG = "HomeScreen"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach: ")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e(TAG, "onConfigurationChanged: $newConfig")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach: ")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save relevant data to the bundle
        outState.putString("location", "binding.rainTemp.text.toString()")
        Log.e(TAG, "onSaveInstanceState: $outState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Restore the saved data
        if (savedInstanceState != null) {
            val savedLocation = savedInstanceState.getString("location")
            if (!savedLocation.isNullOrEmpty() && binding.rainTemp.text.toString().isNotEmpty()) {
                // Use the saved location to restore the state
                binding.rainTemp.text = savedLocation
            }
        }
        Log.e(TAG, "onViewStateRestored: $savedInstanceState")
        Log.e(TAG, "onViewStateRestored: ${savedInstanceState?.getString("location")}")
    }

}
