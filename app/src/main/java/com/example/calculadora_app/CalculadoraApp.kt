package com.example.calculadora_app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class CalculadoraApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

        val nightMode = sharedPreferences.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}