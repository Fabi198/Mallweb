package com.example.mallweb.fragmentsDrawerMenu

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentFAQBinding


@Suppress("DEPRECATION")
class FAQFragment : Fragment(R.layout.fragment_f_a_q) {

    private lateinit var binding: FragmentFAQBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFAQBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")
        val animDownIn: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.down_in_answer)
        val animUpOut: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.up_out_answer)
        val animRotUp: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_up_btn_answer)
        val animRotDown: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_down_btn_answer)

        binding.btnShowAnswer.setOnClickListener {
            if (binding.ansFAQ1.visibility == View.GONE) {
                binding.tvFAQ1.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer.startAnimation(animRotUp)
                binding.sepFAQ1.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ1.visibility = View.VISIBLE
                binding.ansFAQ1.startAnimation(animDownIn)
            } else if (binding.ansFAQ1.visibility == View.VISIBLE) {
                binding.tvFAQ1.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer.startAnimation(animRotDown)
                binding.sepFAQ1.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ1.startAnimation(animUpOut)
                binding.ansFAQ1.visibility = View.GONE
            }
        }

        binding.btnShowAnswer2.setOnClickListener {
            if (binding.ansFAQ2.visibility == View.GONE) {
                binding.tvFAQ2.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer2.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer2.startAnimation(animRotUp)
                binding.sepFAQ2.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ2.visibility = View.VISIBLE
                binding.ansFAQ2.startAnimation(animDownIn)
            } else if (binding.ansFAQ2.visibility == View.VISIBLE) {
                binding.tvFAQ2.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer2.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer2.startAnimation(animRotDown)
                binding.sepFAQ2.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ2.startAnimation(animUpOut)
                binding.ansFAQ2.visibility = View.GONE
            }
        }

        binding.btnShowAnswer3.setOnClickListener {
            if (binding.ansFAQ3.visibility == View.GONE) {
                binding.tvFAQ3.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer3.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer3.startAnimation(animRotUp)
                binding.sepFAQ3.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ3.visibility = View.VISIBLE
                binding.ansFAQ3.startAnimation(animDownIn)
            } else if (binding.ansFAQ3.visibility == View.VISIBLE) {
                binding.tvFAQ3.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer3.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer3.startAnimation(animRotDown)
                binding.sepFAQ3.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ3.startAnimation(animUpOut)
                binding.ansFAQ3.visibility = View.GONE
            }
        }

        binding.btnShowAnswer4.setOnClickListener {
            if (binding.llLinkAns4.visibility == View.GONE) {
                binding.linkansFAQ4.movementMethod = LinkMovementMethod.getInstance()
                binding.linkansFAQ4.setLinkTextColor(Color.YELLOW)
                binding.tvFAQ4.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer4.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer4.startAnimation(animRotUp)
                binding.sepFAQ4.setBackgroundColor(resources.getColor(R.color.red))
                binding.llLinkAns4.visibility = View.VISIBLE
                binding.llLinkAns4.startAnimation(animDownIn)
            } else if (binding.llLinkAns4.visibility == View.VISIBLE) {
                binding.tvFAQ4.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer4.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer4.startAnimation(animRotDown)
                binding.sepFAQ4.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.llLinkAns4.startAnimation(animUpOut)
                binding.llLinkAns4.visibility = View.GONE
            }
        }

        binding.btnShowAnswer5.setOnClickListener {
            if (binding.ansFAQ5.visibility == View.GONE) {
                binding.tvFAQ5.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer5.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer5.startAnimation(animRotUp)
                binding.sepFAQ5.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ5.visibility = View.VISIBLE
                binding.ansFAQ5.startAnimation(animDownIn)
            } else if (binding.ansFAQ5.visibility == View.VISIBLE) {
                binding.tvFAQ5.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer5.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer5.startAnimation(animRotDown)
                binding.sepFAQ5.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ5.startAnimation(animUpOut)
                binding.ansFAQ5.visibility = View.GONE
            }
        }

        binding.btnShowAnswer6.setOnClickListener {
            if (binding.ansFAQ6.visibility == View.GONE) {
                binding.tvFAQ6.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer6.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer6.startAnimation(animRotUp)
                binding.sepFAQ6.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ6.visibility = View.VISIBLE
                binding.ansFAQ6.startAnimation(animDownIn)
            } else if (binding.ansFAQ6.visibility == View.VISIBLE) {
                binding.tvFAQ6.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer6.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer6.startAnimation(animRotDown)
                binding.sepFAQ6.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ6.startAnimation(animUpOut)
                binding.ansFAQ6.visibility = View.GONE
            }
        }

        binding.btnShowAnswer7.setOnClickListener {
            if (binding.ansFAQ7.visibility == View.GONE) {
                binding.tvFAQ7.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer7.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer7.startAnimation(animRotUp)
                binding.sepFAQ7.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ7.visibility = View.VISIBLE
                binding.ansFAQ7.startAnimation(animDownIn)
            } else if (binding.ansFAQ7.visibility == View.VISIBLE) {
                binding.tvFAQ7.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer7.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer7.startAnimation(animRotDown)
                binding.sepFAQ7.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ7.startAnimation(animUpOut)
                binding.ansFAQ7.visibility = View.GONE
            }
        }

        binding.btnShowAnswer8.setOnClickListener {
            if (binding.llLinkAns8.visibility == View.GONE) {
                binding.tvFAQ8.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer8.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer8.startAnimation(animRotUp)
                binding.sepFAQ8.setBackgroundColor(resources.getColor(R.color.red))
                binding.llLinkAns8.visibility = View.VISIBLE
                binding.llLinkAns8.startAnimation(animDownIn)
                binding.linkansFAQ8.setOnClickListener {

                }
            } else if (binding.llLinkAns8.visibility == View.VISIBLE) {
                binding.tvFAQ8.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer8.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer8.startAnimation(animRotDown)
                binding.sepFAQ8.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.llLinkAns8.startAnimation(animUpOut)
                binding.llLinkAns8.visibility = View.GONE
            }
        }

        binding.btnShowAnswer9.setOnClickListener {
            if (binding.ansFAQ9.visibility == View.GONE) {
                binding.tvFAQ9.setTextColor(resources.getColor(R.color.red))
                binding.btnShowAnswer9.setImageResource(R.drawable.baseline_keyboard_arrow_down_red_24)
                binding.btnShowAnswer9.startAnimation(animRotUp)
                binding.sepFAQ9.setBackgroundColor(resources.getColor(R.color.red))
                binding.ansFAQ9.visibility = View.VISIBLE
                binding.ansFAQ9.startAnimation(animDownIn)
            } else if (binding.ansFAQ9.visibility == View.VISIBLE) {
                binding.tvFAQ9.setTextColor(resources.getColor(R.color.grayLight))
                binding.btnShowAnswer9.setImageResource(R.drawable.baseline_keyboard_arrow_up_gray_24)
                binding.btnShowAnswer9.startAnimation(animRotDown)
                binding.sepFAQ9.setBackgroundColor(resources.getColor(R.color.grayLight))
                binding.ansFAQ9.startAnimation(animUpOut)
                binding.ansFAQ9.visibility = View.GONE
            }
        }
    }



}