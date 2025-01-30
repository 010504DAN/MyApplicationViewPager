package com.example.myapplicationviewpager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "note")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String,
    val description: String,
    val date: String,
    val color: String
)