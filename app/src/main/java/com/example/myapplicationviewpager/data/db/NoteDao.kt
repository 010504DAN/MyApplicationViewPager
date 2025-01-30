package com.example.myapplicationviewpager.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplicationviewpager.adapters.OnBoardAdapter
import com.example.myapplicationviewpager.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<NoteEntity>>

    @Insert
    fun insertAll(vararg notes: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: Int): NoteEntity

    @Update
    fun update(note: NoteEntity)

}