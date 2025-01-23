package com.example.myapplicationviewpager.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.myapplicationviewpager.PreferenceHelper
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.databinding.FragmentNewNoteBinding
import com.example.myapplicationviewpager.databinding.FragmentNotesBinding
import com.example.myapplicationviewpager.extensions.setBackStackData

class NewNoteFragment : Fragment() {
    private lateinit var binding: FragmentNewNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        binding.apply {
            btnSave.setOnClickListener{
                val title : String = binding.etTitle.text.toString()
                  setBackStackData("key", title, true)
                val pref = PreferenceHelper()
                pref.unit(requireContext())
                pref.text = title
            }
        }
    }
}