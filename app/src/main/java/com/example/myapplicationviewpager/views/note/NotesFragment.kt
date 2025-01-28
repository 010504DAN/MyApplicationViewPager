package com.example.myapplicationviewpager.views.note

import android.os.Bundle
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
import com.example.myapplicationviewpager.extensions.getBackStackData

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var adapter: NotesAdapter
    private var isLinear = true

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
        initialize()
        getData()
    }

    private fun initialize() {
        adapter = NotesAdapter()
        binding.rvNotes.apply {
            adapter = this@NotesFragment.adapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }

        binding.btnChange.setOnClickListener(){
            adapter.apply {
                if (isLinear){
                    isLinear = false
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(requireContext(),2)
                    binding.btnChange.setImageResource(R.drawable.ic_shape3)
                } else{
                    isLinear = true
                    binding.rvNotes.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                    binding.btnChange.setImageResource(R.drawable.ic_shape2)
                }
            }
        }

        binding.btnAdd.setOnClickListener(){
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToNewNoteFragment())
        }
    }
    private fun getData(){
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){ model ->
            adapter.submitList(model)
            adapter.notifyDataSetChanged()
        }

    }
}
