package com.example.myweatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myweatherapp.databinding.FragmentSettingsScreenBinding

class SettingsScreen : Fragment() {
    private lateinit var binding: FragmentSettingsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate: $savedInstanceState")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "onCreateView: $savedInstanceState")
        // Inflate the layout for this fragment
        binding = FragmentSettingsScreenBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getMyTheme = ThemePreferencesManager.getMyTheme(requireContext())  // getting mobile theme

        Log.e(TAG, "onViewCreated: $savedInstanceState")
        switchMyTheme(getMyTheme)
        binding.themeBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            onThemeSwitchChanged(isChecked)
            //storeMyTheme(binding.themeBtn.isChecked)  // storing my switchValue
        }
        val versionName = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
        binding.versionText.text = "Version: $versionName"
    }

    // Check if the switch Theme exists
    private fun switchMyTheme(myTheme: Boolean) {
        if (myTheme) {
            binding.themeBtn.isChecked = ThemePreferencesManager.getMyTheme(requireContext())
        }
    }

    private fun storeMyTheme(checked: Boolean) {
        ThemePreferencesManager.storeMyTheme(requireContext(), checked)
        Log.e(TAG, "storeMyTheme: $checked")
    }


    private fun onThemeSwitchChanged(checked: Boolean) {
        ThemePreferencesManager.setTheme(requireContext(), checked)
        ThemePreferencesManager.storeMyTheme(requireContext(), checked)
        (requireActivity().application as MyApp).setAppTheme(checked)
        // recreate() // Recreate the activity to apply the new theme

    }
    companion object{
        private const val TAG = "SettingsScreen"
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.e(TAG, "onViewStateRestored: $savedInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, "onSaveInstanceState: $outState")
    }
}