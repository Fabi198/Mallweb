package com.example.mallweb.fragmentsDrawerMenu


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentDeliverMethodsBinding


@Suppress("DEPRECATION")
class DeliverMethodsFragment : Fragment(R.layout.fragment_deliver_methods) {

    private lateinit var binding: FragmentDeliverMethodsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeliverMethodsBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")
        val animDownIn: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.down_in_answer)
        val animUpOut: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.up_out_answer)
        val animRotUp: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_up_btn_answer)
        val animRotDown: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_down_btn_answer)

        binding.btnShowAnswer.setOnClickListener {
            if (binding.ansDelMet1.visibility == View.GONE) {
                binding.tvDelMet1.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer.startAnimation(animRotUp)
                binding.sepDelMet1.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansDelMet1.visibility = View.VISIBLE
                binding.ansDelMet1.startAnimation(animDownIn)
            } else if (binding.ansDelMet1.visibility == View.VISIBLE) {
                binding.tvDelMet1.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer.startAnimation(animRotDown)
                binding.sepDelMet1.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansDelMet1.startAnimation(animUpOut)
                binding.ansDelMet1.visibility = View.GONE
            }
        }

        binding.btnShowAnswer2.setOnClickListener {
            if (binding.ansDelMet2.visibility == View.GONE) {
                binding.tvDelMet2.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer2.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer2.startAnimation(animRotUp)
                binding.sepDelMet2.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansDelMet2.visibility = View.VISIBLE
                binding.ansDelMet2.startAnimation(animDownIn)
            } else if (binding.ansDelMet2.visibility == View.VISIBLE) {
                binding.tvDelMet2.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer2.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer2.startAnimation(animRotDown)
                binding.sepDelMet2.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansDelMet2.startAnimation(animUpOut)
                binding.ansDelMet2.visibility = View.GONE
            }
        }

        binding.btnShowAnswer3.setOnClickListener {
            if (binding.ansDelMet3.visibility == View.GONE) {
                binding.tvDelMet3.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer3.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer3.startAnimation(animRotUp)
                binding.sepDelMet3.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansDelMet3.visibility = View.VISIBLE
                binding.ansDelMet3.startAnimation(animDownIn)
            } else if (binding.ansDelMet3.visibility == View.VISIBLE) {
                binding.tvDelMet3.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer3.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer3.startAnimation(animRotDown)
                binding.sepDelMet3.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansDelMet3.startAnimation(animUpOut)
                binding.ansDelMet3.visibility = View.GONE
            }
        }

    }
}