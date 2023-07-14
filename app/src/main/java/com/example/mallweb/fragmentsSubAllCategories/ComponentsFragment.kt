package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentComponentsBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment

class ComponentsFragment : Fragment(R.layout.fragment_components) {

    private lateinit var binding: FragmentComponentsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComponentsBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnCombosActualizacion.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 9, nameCategory = "COMBOS ACTUALIZACION PC") }
        binding.btnFuentesAlimentacion.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 10, nameCategory = "FUENTES ALIMENTACION") }
        binding.btnMemoriasPc.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 11, nameCategory = "MEMORIAS PARA PC") }
        binding.btnMemoriasNotebook.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 12, nameCategory = "MEMORIAS PARA NOTEBOOK") }
        binding.btnMicroAMD.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 13, nameCategory = "MICROPROCESADORES PARA AMD") }
        binding.btnMicroINTEL.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 14, nameCategory = "MICROPROCESADORES PARA INTEL") }
        binding.btnMotherAMD.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 15, nameCategory = "MOTHER PARA AMD") }
        binding.btnMotherINTEL.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 16, nameCategory = "MOTHER PARA INTEL") }
        binding.btnPlacaDeVideo.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 17, nameCategory = "PLACA DE VIDEO") }
        binding.btnUPS.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 0, nameCategory = "UPS") }
    }


}