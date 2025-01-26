package com.example.myapplicationviewpager.views.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplicationviewpager.PreferenceHelper
import com.example.myapplicationviewpager.adapters.NotesAdapter
import com.example.myapplicationviewpager.databinding.FragmentNotesBinding
import com.example.myapplicationviewpager.extensions.getBackStackData
import com.example.myapplicationviewpager.models.Notes

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NotesAdapter
    private val notes = ArrayList<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initialize()
    }

    private fun initialize() {
        adapter = NotesAdapter(notes)
        binding.rvNotes.adapter = adapter
        /*binding.rvNotes.apply {
            adapter = this@NotesFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }*/

        binding.btnAdd.setOnClickListener(){
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment())
        }

        val pref = PreferenceHelper()
        pref.unit(requireContext())
        pref.text?.let { Notes(it) }?.let { notes.add(it) }
    }
    private fun getData(){
        getBackStackData<String>("key"){
            val note = Notes(it)
            /*notes.add(note)*/
            adapter.submitList(notes)
        }

    }
}
