package com.example.mallweb.fragmentsDrawerMenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentFeaturedBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.fragments.CategoryFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment
import com.example.mallweb.misc.DarkMode.isDarkModeEnabled
import com.squareup.picasso.Picasso

class FeaturedFragment : Fragment(R.layout.fragment_featured) {

    private lateinit var binding: FragmentFeaturedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeaturedBinding.bind(view)

        if (isDarkModeEnabled(requireContext())) { fillLightLogos() } else { fillDarkLogos() }
        onClick()
    }

    private fun onClick() {
        val dbMallweb = DbMallweb(requireContext())
        binding.imageView1.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "AMD", idCArray = dbMallweb.queryForCategoryCant(3), idBrand = 3) }
        binding.imageView2.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "ELGATO", idCArray = dbMallweb.queryForCategoryCant(14), idBrand = 14) }
        binding.imageView3.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "LOGITECH", idCArray = dbMallweb.queryForCategoryCant(27), idBrand = 27) }
        binding.imageView4.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "CORSAIR", idCArray = dbMallweb.queryForCategoryCant(9), idBrand = 9) }
        binding.imageView5.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "GIGABYTE", idCArray = dbMallweb.queryForCategoryCant(19), idBrand = 19) }
        binding.imageView6.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "SEAGATE", idCArray = dbMallweb.queryForCategoryCant(37), idBrand = 37) }
        binding.imageView7.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "CRUCIAL", idCArray = dbMallweb.queryForCategoryCant(10), idBrand = 10) }
        binding.imageView8.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "WESTERN DIGITAL", idCArray = dbMallweb.queryForCategoryCant(43), idBrand = 43) }
        binding.imageView9.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "PATRIOT", idCArray = dbMallweb.queryForCategoryCant(33), idBrand = 33) }
        binding.imageView10.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "ECOVISION", idCArray = dbMallweb.queryForCategoryCant(13), idBrand = 13) }
        binding.imageView11.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "HIKVISION", idCArray = dbMallweb.queryForCategoryCant(21), idBrand = 21) }
        binding.imageView12.setOnClickListener { showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, nameCategory = "INTEL", idCArray = dbMallweb.queryForCategoryCant(22), idBrand = 22) }
    }

    private fun fillDarkLogos() {
        Picasso.get().load(R.drawable.mallweb_brand_logo_amd).resize(800, 800).centerInside().into(binding.imageView1)
        Picasso.get().load(R.drawable.mallweb_brand_logo_elgato).resize(800, 800).centerInside().into(binding.imageView2)
        Picasso.get().load(R.drawable.mallweb_brand_logo_logitech).resize(800, 800).centerInside().into(binding.imageView3)
        Picasso.get().load(R.drawable.mallweb_brand_logo_corsair).resize(800, 800).centerInside().into(binding.imageView4)
        Picasso.get().load(R.drawable.mallweb_brand_logo_gigabyte).resize(800, 800).centerInside().into(binding.imageView5)
        Picasso.get().load(R.drawable.mallweb_brand_logo_seagate).resize(800, 800).centerInside().into(binding.imageView6)
        Picasso.get().load(R.drawable.mallweb_brand_logo_crucial).resize(800, 800).centerInside().into(binding.imageView7)
        Picasso.get().load(R.drawable.mallweb_brand_logo_wd).resize(800, 800).centerInside().into(binding.imageView8)
        Picasso.get().load(R.drawable.mallweb_brand_logo_patriot).resize(800, 800).centerInside().into(binding.imageView9)
        Picasso.get().load(R.drawable.mallweb_brand_logo_ecovision).resize(800, 800).centerInside().into(binding.imageView10)
        Picasso.get().load(R.drawable.mallweb_brand_logo_hikvision).resize(800, 800).centerInside().into(binding.imageView11)
        Picasso.get().load(R.drawable.mallweb_brand_logo_intel).resize(800, 800).centerInside().into(binding.imageView12)
    }

    private fun fillLightLogos() {
        Picasso.get().load(R.drawable.mallweb_brand_logo_amd_light).resize(800, 800).centerInside().into(binding.imageView1)
        Picasso.get().load(R.drawable.mallweb_brand_logo_elgato_light).resize(800, 800).centerInside().into(binding.imageView2)
        Picasso.get().load(R.drawable.mallweb_brand_logo_logitech_light).resize(800, 800).centerInside().into(binding.imageView3)
        Picasso.get().load(R.drawable.mallweb_brand_logo_corsair_light).resize(800, 800).centerInside().into(binding.imageView4)
        Picasso.get().load(R.drawable.mallweb_brand_logo_gigabyte).resize(800, 800).centerInside().into(binding.imageView5)
        Picasso.get().load(R.drawable.mallweb_brand_logo_seagate_light).resize(800, 800).centerInside().into(binding.imageView6)
        Picasso.get().load(R.drawable.mallweb_brand_logo_crucial_light).resize(800, 800).centerInside().into(binding.imageView7)
        Picasso.get().load(R.drawable.mallweb_brand_logo_wd_light).resize(800, 800).centerInside().into(binding.imageView8)
        Picasso.get().load(R.drawable.mallweb_brand_logo_patriot).resize(800, 800).centerInside().into(binding.imageView9)
        Picasso.get().load(R.drawable.mallweb_brand_logo_ecovision).resize(800, 800).centerInside().into(binding.imageView10)
        Picasso.get().load(R.drawable.mallweb_brand_logo_hikvision).resize(800, 800).centerInside().into(binding.imageView11)
        Picasso.get().load(R.drawable.mallweb_brand_logo_intel_light).resize(800, 800).centerInside().into(binding.imageView12)
    }



}






