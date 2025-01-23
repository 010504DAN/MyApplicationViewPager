package com.example.myapplicationviewpager

import android.app.Application
import android.content.SharedPreferences

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }
}