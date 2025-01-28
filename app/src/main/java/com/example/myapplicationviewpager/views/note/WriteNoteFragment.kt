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

class WriteNoteFragment : Fragment() {
    private lateinit var binding: FragmentWriteNoteBinding
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    private val updateTimeTask = object : Runnable {
        override fun run() {
            // Установка текущей даты и времени в TextView
            binding.tvDate.text = dateFormat.format(System.currentTimeMillis())
            handler.postDelayed(this, 1000) // Повторяем обновление через 1 секунду
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWriteNoteBinding.inflate(layoutInflater)
        return binding.root
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
        binding.apply {
            btnSave.setOnClickListener{
                val title : String = binding.etTitle.text.toString()
                val descr : String = binding.etDescr.text.toString()

                if (title.isEmpty() || descr.isEmpty()) {
                    etTitle.error = if (title.isEmpty()) "Введите заголовок" else null
                    etDescr.error = if (descr.isEmpty()) "Введите описание" else null
                    return@setOnClickListener
                }

                val currentDate = binding.tvDate.text.toString()

                App.appDatabase?.noteDao()?.insert(NoteEntity(0,title, descr, currentDate))

                findNavController().navigateUp()
            }
        }
    }
}