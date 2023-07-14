package com.example.mallweb.fragmentsDrawerMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentAllCategoriesBinding
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment
import com.example.mallweb.fragmentsSubAllCategories.*


class AllCategoriesFragment: Fragment(R.layout.fragment_all_categories) {

    private lateinit var binding: FragmentAllCategoriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCategoriesBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.tvAlmacenamiento.setOnClickListener { showFragmentFromFragment(requireActivity(), StorageMenu(), "StorageMenu", id) }
        binding.tvConectividad.setOnClickListener { showFragmentFromFragment(requireActivity(), ConnectivityFragment(), "ConnectivityFragment", id) }
        binding.tvComponentesParaPC.setOnClickListener { showFragmentFromFragment(requireActivity(), ComponentsFragment(), "ComponentsFragment", id) }
        binding.tvPerifericos.setOnClickListener { showFragmentFromFragment(requireActivity(), PeripheralsMenu(), "PeripheralsMenu", id) }
        binding.tvAudioYVideo.setOnClickListener { showFragmentFromFragment(requireActivity(), AudioAndVideoFragment(), "AudioAndVideoFragment", id) }
        binding.tvImpresion.setOnClickListener { showFragmentFromFragment(requireActivity(), PrintFragment(), "PrintFragment", id) }
        binding.tvNotebooks.setOnClickListener { showFragmentFromFragment(requireActivity(), NotebooksFragment(), "NotebooksFragment", id) }
        binding.tvMonitores.setOnClickListener { showFragmentFromFragment(requireActivity(), MonitorFragment(), "MonitorFragment", id) }
        binding.tvZonaGamer.setOnClickListener { showFragmentFromFragment(requireActivity(), GamerZoneFragment(), "GamerZoneFragment", id) }
    }

}