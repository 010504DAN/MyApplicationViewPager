package com.example.myapplicationviewpager.views.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationviewpager.R
import com.example.myapplicationviewpager.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val onBoardPosition = requireArguments().getInt(ARG_ONBOARD_POSITION)
        when (onBoardPosition){
            0 -> {
                binding.tvTitle2.text = getString(R.string.convenience)
                binding.tvDesc.text = getString(R.string.convenience_desc)
                binding.lottieAnim.setAnimation(R.raw.animation3)
                binding.lottieAnim.playAnimation()
            }
            1 -> {
                binding.tvTitle2.text = getString(R.string.organization)
                binding.tvDesc.text = getString(R.string.organization_desc)
                binding.lottieAnim.setAnimation(R.raw.animation2)
                binding.lottieAnim.playAnimation()
            }
            2 -> {
                binding.tvTitle2.text = getString(R.string.synchronization)
                binding.tvDesc.text = getString(R.string.synchronization_desc)
                binding.lottieAnim.setAnimation(R.raw.animation1)
                binding.lottieAnim.playAnimation()
            }
        }

    }

    companion object{
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}