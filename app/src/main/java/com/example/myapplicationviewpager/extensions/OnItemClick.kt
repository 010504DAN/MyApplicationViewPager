package com.example.myapplicationviewpager.extensions

import com.example.myapplicationviewpager.data.model.NoteEntity

interface OnItemClick {
    fun onClick(note: NoteEntity)
    fun onLongClick(note: NoteEntity)
}