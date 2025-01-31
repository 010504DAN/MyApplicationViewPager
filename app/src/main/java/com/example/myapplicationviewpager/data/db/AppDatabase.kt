package com.example.myapplicationviewpager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplicationviewpager.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

}