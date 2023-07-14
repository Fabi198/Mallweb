package com.example.mallweb.fragmentsDrawerMenu


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentContactUsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class ContactUsFragment : Fragment(R.layout.fragment_contact_us), OnMapReadyCallback {

    private lateinit var binding: FragmentContactUsBinding
    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactUsBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")

        binding.tvContactMail.movementMethod = LinkMovementMethod.getInstance()
        binding.tvContactMail.setLinkTextColor(Color.GRAY)
        binding.tvContactCellphone.movementMethod = LinkMovementMethod.getInstance()
        binding.tvContactCellphone.setLinkTextColor(Color.GRAY)
        binding.btnMailMallweb.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:venta@mallweb.com.ar"))) }
        binding.btnWhatsappMallweb.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/5491147022760"))) }
        binding.btnInstagram.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mallweb.com.ar/"))) }
        binding.btnFacebook.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mallweb.com.ar"))) }
        binding.btnYoutube.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/MallWebArgentina"))) }
        binding.btnAFIP.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://qr.afip.gob.ar/?qr=AukXbT4tGNymPpOCAbsFnA,,"))) }
        val fragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        addMarker()
    }

    private fun addMarker() {
        val latLong = LatLng(-34.5487442, -58.473381)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 14f))
    }


}