package com.example.myapplicationviewpager.views.note

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplicationviewpager.App
import com.example.myapplicationviewpager.PreferenceHelper
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.adapters.NotesAdapter
import com.example.myapplicationviewpager.data.model.NoteEntity
import com.example.myapplicationviewpager.databinding.FragmentNotesBinding
import com.example.myapplicationviewpager.extensions.OnItemClick
import com.example.myapplicationviewpager.extensions.getBackStackData
import com.example.myapplicationviewpager.views.SingInFragment.Companion.TAG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NotesAdapter
    private var isLinear = true
    private var noteList: ArrayList<NoteEntity> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        getData()
    }

    private fun initialize() {
        adapter = NotesAdapter(
            onClick = { note -> onClick(note) },
            onLongClick = { note -> onLongClick(note) }
        )

        binding.rvNotes.apply {
            adapter = this@NotesFragment.adapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }

        binding.ivChange.setOnClickListener {
            if (isLinear) {
                isLinear = false
                binding.rvNotes.layoutManager =
                    androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
                binding.ivChange.setImageResource(R.drawable.ic_shape3)
            } else {
                isLinear = true
                binding.rvNotes.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                binding.ivChange.setImageResource(R.drawable.ic_shape2)
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterNotes(p0?.toString() ?: "")
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment())
        }
    }

    private fun getData() {
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        if (pref.isAnonymous) {
            App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { model ->
                noteList.clear()
                noteList.addAll(model)
                adapter.submitList(noteList)
                adapter.notifyDataSetChanged()
            }
        } else {
            noteList.clear()
            val db = Firebase.firestore
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val title = document.data["title"]?.toString() ?: ""
                        val description = document.data["description"]?.toString() ?: ""
                        val date = document.data["date"]?.toString() ?: ""
                        val color = document.data["color"]?.toString() ?: "#B79BFD"
                        val note = NoteEntity(0, title, description, date, color)
                        noteList.add(note)
                    }
                    adapter.submitList(noteList)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }

    private fun filterNotes(query: String) {
        val filteredList = if (query.isEmpty()) {
            noteList // Return the full list if the query is empty
        } else {
            noteList.filter { note ->
                (note.title?.contains(query, ignoreCase = true) == true) ||
                        (note.description?.contains(query, ignoreCase = true) == true)
            }
        }
        adapter.submitList(filteredList)
    }

    private fun onClick(note: NoteEntity) {
        throw RuntimeException("Test Crash")
        findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment(note.id))
    }

    private fun onLongClick(note: NoteEntity) {
        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        builder
            ?.setMessage("Are you sure you want to delete this note?")
            ?.setTitle("Delete Note")
            ?.setPositiveButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            ?.setNegativeButton("Delete") { dialog, _ ->
                App.appDatabase?.noteDao()?.delete(note)
                val db = Firebase.firestore
                db.collection("notes").document(note.id.toString())
                    .delete()
                    .addOnSuccessListener {
                        Log.e(TAG, "DocumentSnapshot successfully deleted!")
                        noteList.remove(note)
                        adapter.submitList(noteList)
                        adapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error deleting document", e)
                    }
            }

        val dialog: AlertDialog = builder!!.create()
        dialog.show()
    }
}