package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentPeripheralsMenuBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment


class PeripheralsMenu : Fragment(R.layout.fragment_peripherals_menu) {

    private lateinit var binding: FragmentPeripheralsMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPeripheralsMenuBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnAuriculares.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 32, nameCategory = "AURICULARES") }
        binding.btnAuricularesConMicrofono.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 33, nameCategory = "AURICULARES CON MICROFONO") }
        binding.btnCamaraWeb.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 34, nameCategory = "CAMARA WEB") }
        binding.btnMouse.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 35, nameCategory = "MOUSES") }
        binding.btnMouseOptico.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 36, nameCategory = "MOUSE OPTICO") }
        binding.btnTabletas.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 37, nameCategory = "TABLETAS DIGITALIZADORAS") }
        binding.btnTeclado.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 38, nameCategory = "TECLADOS") }
        binding.btnTecladoMouse.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 39, nameCategory = "TECLADO + MOUSE") }
        binding.btnTecladoInalambrico.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 40, nameCategory = "TECLADO INALAMBRICO") }

    }

}