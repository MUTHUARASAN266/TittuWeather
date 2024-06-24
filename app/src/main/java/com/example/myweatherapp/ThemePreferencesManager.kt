package com.example.myweatherapp

import android.content.Context
import android.content.SharedPreferences

object ThemePreferencesManager {
    private const val PREF_NAME = "theme_preferences"
    private const val LIGHT_MODE = "light_mode"
    private const val Store_My_Switch = "switchValue"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    }
    fun containsLightMode(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.contains(LIGHT_MODE)
    }
    fun getTheme(context: Context):Boolean{
         return getSharedPreferences(context).getBoolean(LIGHT_MODE,false)
    }
    fun setTheme(context: Context,isLightMode:Boolean){
        val edit=getSharedPreferences(context).edit()
        edit.putBoolean(LIGHT_MODE,isLightMode).apply()
    }
    fun storeThemeValue(context: Context,state:Boolean){
        val edit=getSharedPreferences(context).edit()
        edit.putBoolean(LIGHT_MODE,state).apply()
    }
    fun getThemeValue(context: Context):Boolean{
        return getSharedPreferences(context).getBoolean(LIGHT_MODE,false)

    }
    fun storeMyTheme(context: Context,state: Boolean){
        val edit=getSharedPreferences(context).edit()
        edit.putBoolean(Store_My_Switch,state).apply()
    }
    fun getMyTheme(context: Context):Boolean{
        return getSharedPreferences(context).getBoolean(Store_My_Switch,false)
    }
}