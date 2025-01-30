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

class NotesFragment : Fragment(), OnItemClick {
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
        adapter = NotesAdapter(this)
        binding.rvNotes.apply {
            adapter = this@NotesFragment.adapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }

        binding.ivChange.setOnClickListener(){
            adapter.apply {
                if (isLinear){
                    isLinear = false
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(requireContext(),2)
                    binding.ivChange.setImageResource(R.drawable.ic_shape3)
                } else{
                    isLinear = true
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                    binding.ivChange.setImageResource(R.drawable.ic_shape2)
                }
            }

            binding.etSearch.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filterNotes(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
        }

        binding.btnAdd.setOnClickListener(){
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment())
        }
    }
    private fun getData(){
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        if (pref.isAnonymous){
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){ model ->
            noteList.addAll(model)
            adapter.submitList(noteList)
            adapter.notifyDataSetChanged()
        }
        } else {
                val db = Firebase.firestore
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val title = document.data["title"].toString()
                        val description = document.data["description"].toString()
                        val date = document.data["date"].toString()
                        val color = document.data["color"].toString()
                        val note = NoteEntity(0, title, description, date, color)
                        noteList.add(note)
                        adapter.submitList(noteList)
                        adapter.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }


    private fun filterNotes(query: String){
        val filteredList: List<NoteEntity> = noteList.filter { note ->
            note.title.contains(query, ignoreCase = true) || note.title.contains(query, ignoreCase = true)
        }
        adapter.submitList(filteredList)
    }

    override fun onClick(note: NoteEntity) {
        findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment(note.id))
    }

    override fun onLongClick(note: NoteEntity) {
        val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
        builder
            ?.setMessage("I am the message")
            ?.setTitle("I am the title")
            ?.setPositiveButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            ?.setNegativeButton("Delete") { dialog, which ->
                App.appDatabase?.noteDao()?.delete(note)
            }

        val dialog: AlertDialog = builder!!.create()
        dialog.show()
    }
}
