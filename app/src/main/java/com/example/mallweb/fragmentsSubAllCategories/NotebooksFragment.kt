package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentNotebooksBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment


class NotebooksFragment : Fragment(R.layout.fragment_notebooks) {

    private lateinit var binding: FragmentNotebooksBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotebooksBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnNotebooks.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 30, nameCategory = "NOTEBOOKS") }
        binding.btnRepuestos.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 31, nameCategory = "REPUESTOS") }
        binding.btnBaterias.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 29, nameCategory = "BATERIAS") }
        binding.btnBaseNotebooks.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 28, nameCategory = "BASE PARA NOTEBOOK") }


    }

}