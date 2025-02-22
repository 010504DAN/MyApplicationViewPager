package com.example.myapplicationviewpager

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences

    fun unit (context: Context){
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    /*var text: String?
        get() = sharedPreferences.getString("text", "")
        set(value) = sharedPreferences.edit().putString("text",value).apply()*/

    var isOnBoardShown: Boolean
        get() = sharedPreferences.getBoolean("onBoard", false)
        set(value) = sharedPreferences.edit().putBoolean("onBoard", value).apply()

    var isAnonymous: Boolean
        get() = sharedPreferences.getBoolean("anonymous", false)
        set(value) = sharedPreferences.edit().putBoolean("anonymous", value).apply()

}