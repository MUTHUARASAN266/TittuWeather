package com.example.myweatherapp

import android.annotation.SuppressLint
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsScreenBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getMyTheme = ThemePreferencesManager.getMyTheme(requireContext())  // getting mobile theme

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
}