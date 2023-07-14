package com.example.mallweb.auth

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentRegisterBinding
import com.example.mallweb.db.DbMallweb
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")
        setup()
    }

    private fun setup() {
        binding.btnReg.setOnClickListener {
            if (areEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val dbMallweb = DbMallweb(requireContext())
                            dbMallweb.createBasicClient(binding.etEmail.text.toString(),
                            binding.etName.text.toString(),
                            binding.etDNI.text.toString(),
                            binding.etCUIT.text.toString())
                            showAlertSuccess()
                            requireActivity().supportFragmentManager.popBackStack()
                        } else {
                            showAlert()
                        }
                    }

            } else {
                Toast.makeText(requireContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun areEmpty(): Boolean {
        return binding.etEmail.text.isNotEmpty()
                && binding.etPass.text.isNotEmpty()
                && binding.etCUIT.text.isNotEmpty()
                && binding.etDNI.text.isNotEmpty()
                && binding.etName.text.isNotEmpty()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Enviado")
        builder.setMessage("Ya se encuentra registrado, ingrese con su usuario y contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}