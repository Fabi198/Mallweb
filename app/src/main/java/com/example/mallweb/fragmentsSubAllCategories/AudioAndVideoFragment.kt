package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentAudioAndVideoBinding
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.objects.ShowFragment.showFragmentFromFragment


class AudioAndVideoFragment : Fragment(R.layout.fragment_audio_and_video) {

    private lateinit var binding: FragmentAudioAndVideoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAudioAndVideoBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        binding.btnBarraDeSonido.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 5, nameCategory = "BARRA DE SONIDO") }
        binding.btnParlantes.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 6, nameCategory = "PARLANTES") }
        binding.btnSoportes.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 7, nameCategory = "SOPORTES") }
        binding.btnTVLCDLED.setOnClickListener { showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = 8, nameCategory = "TV LCD/LED") }

    }

}