package com.example.mallweb.fragmentsDrawerMenu

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentBankDataBinding


class BankDataFragment : Fragment(R.layout.fragment_bank_data) {

    private lateinit var binding: FragmentBankDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBankDataBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")

        binding.btnCopyCBU.setOnClickListener {
            val clip = ClipData.newPlainText("text", "0720432020000000074386")
            val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Copiado al portapapeles!", Toast.LENGTH_LONG).show()
        }

    }

}