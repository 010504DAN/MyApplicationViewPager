package com.example.myapplicationviewpager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.data.model.NoteEntity
import com.example.myapplicationviewpager.databinding.ItemNoteBinding
import com.example.myapplicationviewpager.extensions.OnItemClick

class NotesAdapter(
    /*private val onItemClick: (NoteEntity) -> Unit,
    private val onItemLongClick: (NoteEntity) -> Unit,*/
    private val customOnClick: OnItemClick
) : ListAdapter<NoteEntity,NotesAdapter.NotesViewHolder>(DiffCallback()) {

    inner class NotesViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder (binding.root) {
        fun bind(notes: NoteEntity){
            binding.tvTitle.text = notes.title
            binding.tvDescr.text = notes.description
            binding.tvDate.text = notes.date
            binding.root.setBackgroundColor(android.graphics.Color.parseColor(notes.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem((position)))

        holder.itemView.setOnClickListener{
            customOnClick.onClick(getItem(position))
        }

        holder.itemView.setOnLongClickListener{
            customOnClick.onLongClick(getItem(position))
            true
        }
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