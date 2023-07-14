package com.example.mallweb.auth

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentRecoverPassBinding
import com.google.firebase.auth.FirebaseAuth


class RecoverPassFragment : Fragment(R.layout.fragment_recover_pass) {

    private lateinit var binding: FragmentRecoverPassBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecoverPassBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")
        val mAuth = FirebaseAuth.getInstance()


        binding.btnRecoverPass.setOnClickListener {
            if (binding.etEmailRecoverPass.text.isNotEmpty()) {
                resetPassword(mAuth, binding.etEmailRecoverPass.text.toString())
            } else {
                Toast.makeText(requireContext(), "Debe ingresar un email", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun resetPassword(mAuth: FirebaseAuth, email: String) {
        mAuth.setLanguageCode("es")
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                showAlertSuccess()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                showAlert()
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("No se pudo enviar el correo")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Enviado")
        builder.setMessage("Se ha enviado el correo, revise su casilla")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}