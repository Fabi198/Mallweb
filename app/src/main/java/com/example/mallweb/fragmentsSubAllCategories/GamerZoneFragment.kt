package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentGamerZoneBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment


class GamerZoneFragment : Fragment(R.layout.fragment_gamer_zone) {

    private lateinit var binding: FragmentGamerZoneBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamerZoneBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnAuricularGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 41, nameCategory = "AURICULAR GAMER") }
        binding.btnComponentesStreaming.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 42, nameCategory = "COMPONENTES PARA STREAMING") }
        binding.btnGabinetesGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 43, nameCategory = "GABINETES GAMER") }
        binding.btnMouseGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 44, nameCategory = "MOUSE GAMER") }
        binding.btnPadMouseGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 45, nameCategory = "PAD MOUSE GAMER") }
        binding.btnSillaGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 46, nameCategory = "SILLA GAMER") }
        binding.btnVolantesGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 47, nameCategory = "VOLANTES Y JOYSTICK GAMER") }

    }

}