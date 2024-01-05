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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myweatherapp.MyApp
import com.example.myweatherapp.R
import com.example.myweatherapp.ThemePreferencesManager
import com.example.myweatherapp.WeatherResult
import com.example.myweatherapp.WeatherViewModel
import com.example.myweatherapp.databinding.ActivityHomeScreenBinding
import com.example.myweatherapp.getWeatherDescriptionCode
import com.example.myweatherapp.show
import com.example.myweatherapp.snack
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private val TAG = "HomeScreen"
    private lateinit var navHostController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentActivity.id) as NavHostFragment
        navHostController = navHostFragment.navController

        bottomNavigationView = binding.bottomNavi
        bottomNavigationView.setupWithNavController(navHostController)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle Up navigation by delegating it to the NavController
        return navHostController.navigateUp() || super.onSupportNavigateUp()
    }


}


