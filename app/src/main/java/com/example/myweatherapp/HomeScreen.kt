package com.example.myweatherapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var binding: FragmentHomeScreenBinding
    private lateinit var progressDialog: ProgressDialog
    private val viewModel: WeatherViewModel by viewModels()
    private val TAG = "HomeScreen"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = ProgressDialog(context)
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

        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentWeather(location: String) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.weatherData(com.example.myweatherapp.HomeScreen.API_KEY, location)
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {
                        is WeatherResult.Success -> {
                            // Handle the weather response
                            val cityName = result.response?.location?.name
                            val weatherCode = result.response?.current?.weatherCode
                            val temperature = result.response?.current?.temperature
                            val weatherDescriptions =
                                result.response?.current?.weatherDescriptions?.get(0)
                            // Log something with the weather data
                            val degreeSymbol = "\u00b0"
                            Log.e(TAG, "City: $cityName")
                            Log.e(TAG, "Weather: $weatherDescriptions")
                            Log.e(TAG, "Temperature: $temperature$degreeSymbol")
                            binding.apply {
                                showWeatherDetails()
                                binding.cityName.text = result.response?.location?.name
                                weatherName.text =
                                    result.response?.current?.weatherDescriptions?.get(0)
                                rainTemp.text =
                                    "${result.response?.current?.temperature}$degreeSymbol"
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