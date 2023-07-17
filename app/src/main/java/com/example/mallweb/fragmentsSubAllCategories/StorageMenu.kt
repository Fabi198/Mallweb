package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentStorageMenuBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.objects.ShowFragment.showFragmentFromFragment


class StorageMenu : Fragment(R.layout.fragment_storage_menu) {

    private lateinit var binding: FragmentStorageMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStorageMenuBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnDiscosSSD.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 3, nameCategory = "DISCOS SSD") }
        binding.btnDiscosExternos.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 1, nameCategory = "DISCOS EXTERNOS") }
        binding.btnDiscosSATA.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 2, nameCategory = "DISCOS SATA") }
        binding.btnPendrives.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 4, nameCategory = "PENDRIVES") }
        binding.btnMemoriasSD.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 0, nameCategory = "MEMORIAS SD") }

    }


}