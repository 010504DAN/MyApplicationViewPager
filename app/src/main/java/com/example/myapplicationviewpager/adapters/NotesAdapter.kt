package com.example.myapplicationviewpager.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.data.model.NoteEntity
import com.example.myapplicationviewpager.databinding.ItemNoteBinding

class NotesAdapter(
    private val onClick: (NoteEntity) -> Unit,
    private val onLongClick: (NoteEntity) -> Unit
) : ListAdapter<NoteEntity, NotesAdapter.NotesViewHolder>(DiffCallback()) {

    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteEntity) {
            binding.tvTitle.text = note.title ?: ""
            binding.tvDescr.text = note.description ?: ""
            binding.tvDate.text = note.date ?: ""
            binding.bgNote.setBackgroundColor(parseColor(note.color))

            // Set click listeners
            binding.root.setOnClickListener { onClick(note) }
            binding.root.setOnLongClickListener {
                onLongClick(note)
                true
            }
        }

        private fun parseColor(color: String?): Int {
            return try {
                android.graphics.Color.parseColor(color ?: "#B79BFD") // Default color if null
            } catch (e: IllegalArgumentException) {
                Log.e("NotesAdapter", "Invalid color: $color", e)
                android.graphics.Color.parseColor("#B79BFD") // Default color if invalid
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.description == newItem.description &&
                    oldItem.date == newItem.date &&
                    oldItem.color == newItem.color
        }
    }
}