package com.example.myapplicationviewpager.views.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplicationviewpager.PreferenceHelper
import com.example.myapplicationviewpager.adapters.OnBoardAdapter
import com.example.myapplicationviewpager.databinding.FragmentPagerItemBinding

class PagerItemFragment : Fragment() {
    private lateinit var binding: FragmentPagerItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardAdapter(this@PagerItemFragment)
        binding.circleIndicator.setViewPager(binding.viewPager)
    }

    private fun setupListener() = with(binding.viewPager) {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2){
                    binding.viewPagerBtn.visibility = View.VISIBLE
                    binding.tvSkip.visibility = View.INVISIBLE
                    binding.viewPagerBtn.text = "Начать"
                    binding.viewPagerBtn.setOnClickListener{
                        val pref = PreferenceHelper()
                        pref.unit(requireContext())
                        pref.isOnBoardShown = true
                        findNavController().navigate(PagerItemFragmentDirections.actionPagerItemFragmentToNotesFragment())
                    }
                } else{
                    binding.tvSkip.visibility = View.VISIBLE
                    binding.viewPagerBtn.visibility = View.INVISIBLE
                    binding.tvSkip.setOnClickListener{
                        val pref = PreferenceHelper()
                        pref.unit(requireContext())
                        pref.isOnBoardShown = true
                        findNavController().navigate(PagerItemFragmentDirections.actionPagerItemFragmentToNotesFragment())
                    }
                }
            }
        })
        binding.viewPagerBtn.setOnClickListener{
            if (currentItem < 4){
                setCurrentItem(currentItem + 3, true)
            }
        }
    }
}