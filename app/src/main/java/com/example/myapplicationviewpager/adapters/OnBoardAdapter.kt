package com.example.myapplicationviewpager.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplicationviewpager.views.onBoard.OnBoardFragment

class OnBoardAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = OnBoardFragment().apply {
        arguments = Bundle().apply {
            putInt(OnBoardFragment.ARG_ONBOARD_POSITION, position)
        }
    }
}