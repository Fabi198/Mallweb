package com.example.mallweb.fragmentsDrawerMenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentPromosBinding


@Suppress("DEPRECATION")
class PromosFragment : Fragment(R.layout.fragment_promos) {

    private lateinit var binding: FragmentPromosBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPromosBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")

        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.left_in)
        val anim2: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.left_in)
        val anim3: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.left_in)
        val anim4: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.left_in)

        val handler = Handler()



        handler.postDelayed({
            binding.banner1.visibility = View.VISIBLE
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    handler.postDelayed({
                        anim2.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                                handler.postDelayed({
                                    anim3.setAnimationListener(object : Animation.AnimationListener {
                                        override fun onAnimationStart(animation: Animation?) {
                                            handler.postDelayed({
                                                binding.banner4.visibility = View.VISIBLE
                                                binding.banner4.startAnimation(anim4)
                                            }, 500)
                                        }
                                        override fun onAnimationEnd(animation: Animation?) {}
                                        override fun onAnimationRepeat(animation: Animation?) {}
                                    })
                                    binding.banner3.visibility = View.VISIBLE
                                    binding.banner3.startAnimation(anim3)
                                }, 500)
                            }
                            override fun onAnimationEnd(animation: Animation?) {}
                            override fun onAnimationRepeat(animation: Animation?) {}
                        })
                        binding.banner2.visibility = View.VISIBLE
                        binding.banner2.startAnimation(anim2)
                    }, 500)
                }
                override fun onAnimationEnd(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            binding.banner1.startAnimation(anim)
        }, 300)


        /*
        handler.postDelayed({
            binding.banner2.visibility = View.VISIBLE
            binding.banner2.startAnimation(anim)
        }, 2000)

        handler.postDelayed({
            binding.banner3.visibility = View.VISIBLE
            binding.banner3.startAnimation(anim)
        }, 3000)

        handler.postDelayed({
            binding.banner4.visibility = View.VISIBLE
            binding.banner4.startAnimation(anim)
        }, 4000)

         */




    }
}