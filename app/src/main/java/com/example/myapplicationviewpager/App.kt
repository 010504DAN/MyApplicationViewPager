package com.example.myapplicationviewpager

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.myapplicationviewpager.data.db.AppDatabase

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        getInstance()
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(this)
    }

    private fun getInstance(): AppDatabase? {
        if (appDatabase == null){
            appDatabase =applicationContext?.let { context ->
                Room.databaseBuilder(
                    context,
                    AppDatabase:: class.java,
                    "note.Database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDatabase
    }

    companion object{
        var appDatabase: AppDatabase? = null
    }
}