package com.example.myapplicationviewpager.views.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.databinding.FragmentColorBinding
import com.example.myapplicationviewpager.extensions.setBackStackData

class ColorFragment : Fragment() {
    private lateinit var binding: FragmentColorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            colorLightYellow.setOnClickListener { setColor(R.drawable.bg_light_yellow.toString()) }
            colorGreen.setOnClickListener { setColor(R.drawable.bg_green.toString()) }
            colorBlue.setOnClickListener { setColor(R.drawable.bg_blue.toString()) }
            colorLightRed.setOnClickListener { setColor(R.drawable.bg_light_red.toString()) }
            colorPink.setOnClickListener { setColor(R.drawable.bg_pink.toString()) }
            colorViolet.setOnClickListener { setColor(R.drawable.bg_violet.toString()) }
        }
    }

    private fun setColor(color: String) {
        setBackStackData("selectedColor", color, true)
        findNavController().navigateUp()
    }

}
