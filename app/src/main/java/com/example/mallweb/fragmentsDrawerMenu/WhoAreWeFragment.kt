package com.example.mallweb.fragmentsDrawerMenu

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentWhoAreWeBinding


class WhoAreWeFragment : Fragment(R.layout.fragment_who_are_we) {

    private lateinit var binding: FragmentWhoAreWeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWhoAreWeBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")

        binding.linkFacebookMallweb.movementMethod = LinkMovementMethod.getInstance()
        binding.linkFacebookMallweb.setLinkTextColor(Color.RED)
        binding.linkInstagramMallweb.movementMethod = LinkMovementMethod.getInstance()
        binding.linkInstagramMallweb.setLinkTextColor(Color.RED)
        binding.linkForm.movementMethod = LinkMovementMethod.getInstance()
        binding.linkForm.setLinkTextColor(Color.RED)
        binding.linkMailMallweb.movementMethod = LinkMovementMethod.getInstance()
        binding.linkMailMallweb.setLinkTextColor(Color.RED)
        binding.linkMailMallwebVentas.movementMethod = LinkMovementMethod.getInstance()
        binding.linkMailMallwebVentas.setLinkTextColor(Color.RED)
        binding.linkWhatsappMallweb.movementMethod = LinkMovementMethod.getInstance()
        binding.linkWhatsappMallweb.setLinkTextColor(Color.RED)
        binding.linkTelegramMallweb.movementMethod = LinkMovementMethod.getInstance()
        binding.linkTelegramMallweb.setLinkTextColor(Color.RED)
        binding.linkWhatsappMallweb2.movementMethod = LinkMovementMethod.getInstance()
        binding.linkWhatsappMallweb2.setLinkTextColor(Color.RED)
        binding.linkWhatsappMallweb3.movementMethod = LinkMovementMethod.getInstance()
        binding.linkWhatsappMallweb3.setLinkTextColor(Color.RED)
        binding.linkWhatsappMallweb4.movementMethod = LinkMovementMethod.getInstance()
        binding.linkWhatsappMallweb4.setLinkTextColor(Color.RED)
        binding.linkPhoneMallweb.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:01147022760"))) }
        binding.linkPhoneMallweb.setLinkTextColor(Color.RED)
    }
}