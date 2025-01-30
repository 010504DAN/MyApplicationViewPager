package com.example.myapplicationviewpager.views.note

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.myapplicationviewpager.App
import com.example.myapplicationviewpager.PreferenceHelper
import com.example.myapplicationviewpager.data.model.NoteEntity
import com.example.myapplicationviewpager.databinding.FragmentWriteNoteBinding
import com.example.myapplicationviewpager.extensions.setBackStackData
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.myapplicationviewpager.R
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WriteNoteFragment : Fragment() {
    private lateinit var binding: FragmentWriteNoteBinding
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault())
    private var selectedColor: String = "#B79BFD"
    private var isEdit = false

    private val updateTimeTask = object : Runnable {
        override fun run() {
            binding.tvDate.text = dateFormat.format(System.currentTimeMillis())
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateTimeTask)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startRealTimeClock()
        setListener()
    }

    private fun startRealTimeClock() {
        handler.post(updateTimeTask)
    }

    private fun stopRealTimeClock() {
        handler.removeCallbacks(updateTimeTask)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setListener() {
        val pref = PreferenceHelper()
        pref.unit(requireContext())
        if (pref.isAnonymous){
        binding.apply {
            val args: WriteNoteFragmentArgs = WriteNoteFragmentArgs.fromBundle(requireArguments())
            App.appDatabase?.noteDao()?.getById(args.note)
                ?.let {
                    etTitle.setText(it.title)
                    etDescr.setText(it.description)
                    tvDate.text = it.date
                    selectedColor = it.color
                    isEdit = true
                }

        }

        binding.apply {
            btnBack.setOnClickListener{
                findNavController().navigateUp()
            }
            btnAdd.setOnClickListener {
                showColorMenu(it)
            }
            btnSave.setOnClickListener {
                if (binding.etTitle.text.toString() != "") {
                    val title: String = binding.etTitle.text.toString()
                    val descr: String = binding.etDescr.text.toString()
                    val currentDate = binding.tvDate.text.toString()
                    val note = NoteEntity(0, title, descr, currentDate, selectedColor)

                    if (isEdit) {
                        val args: WriteNoteFragmentArgs =
                            WriteNoteFragmentArgs.fromBundle(requireArguments())
                        note.id = args.note
                        App.appDatabase?.noteDao()?.update(note)
                        findNavController().navigateUp()
                    } else {
                        App.appDatabase?.noteDao()?.insert(note)
                        findNavController().navigateUp()
                    }
                }
            }
            }
        } else {
            binding.btnSave.setOnClickListener{
            val db = Firebase.firestore
            val currentDate = binding.tvDate.text.toString()
            val note = hashMapOf(
                "title" to binding.etTitle.text.toString(),
                "description" to binding.etDescr.text.toString(),
                "date" to currentDate,
                "color" to selectedColor
            )

            db.collection("notes")
                .add(note)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    findNavController().navigateUp()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun showColorMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.menu_color_selection)

        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.color_light_yellow -> selectedColor = "#FFF9C4"
                R.id.color_green -> selectedColor = "#C8E6C9"
                R.id.color_blue -> selectedColor = "#BBDEFB"
                R.id.color_light_red -> selectedColor = "#FFCDD2"
                R.id.color_pink -> selectedColor = "#F8BBD0"
                R.id.color_violet -> selectedColor = "#E1BEE7"
            }
            true
        }
        popupMenu.show()
    }

    companion object{
        private const val TAG = "zizi"
    }
}