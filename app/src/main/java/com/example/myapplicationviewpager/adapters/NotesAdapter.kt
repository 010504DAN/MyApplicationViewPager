package com.example.myapplicationviewpager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.data.model.NoteEntity
import com.example.myapplicationviewpager.databinding.ItemNoteBinding

class NotesAdapter : ListAdapter<NoteEntity,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    private val backgrounds = listOf(
        R.drawable.bg_blue,
        R.drawable.bg_green,
        R.drawable.bg_light_grey,
        R.drawable.bg_light_yellow,
        R.drawable.bg_pink,
        R.drawable.bg_violet
    )

    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder (binding.root) {
        fun bind(notes: NoteEntity){
            binding.tvTitle.text = notes.title
            binding.tvDescr.text = notes.description
            binding.tvDate.text = notes.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val background: Int = backgrounds[position % backgrounds.size]
        holder.itemView.setBackgroundColor(background)
        holder.bind(getItem((position)))
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.title == newItem.title
        }

    }
}