package com.example.mallweb.auth

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentRegisterBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.misc.DarkMode.isDarkModeEnabled
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private var visiblePass = false
    private var visibleConfirmPass = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        // val id = arguments?.getInt("ContainerID")
        setup()
    }

    private fun setup() {

        binding.confirmPasswordTextInputLayout.boxBackgroundColor = Color.TRANSPARENT
        if (isDarkModeEnabled(requireContext())) { binding.confirmPasswordEditText.setTextColor(Color.WHITE) } else { binding.confirmPasswordEditText.setTextColor(Color.BLACK) }

        binding.btnSeePass.setOnClickListener {
            if (!visiblePass) {
                binding.etPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnSeePass.setImageResource(R.drawable.baseline_visibility_off_graylight_24)
                visiblePass = true
            } else {
                binding.etPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnSeePass.setImageResource(R.drawable.baseline_visibility_graylight_24)
                visiblePass = false
            }
        }

        binding.btnSeeConfirmPass.setOnClickListener {
            if (!visibleConfirmPass) {
                binding.confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnSeeConfirmPass.setImageResource(R.drawable.baseline_visibility_off_24)
                visiblePass = true
            } else {
                binding.confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.btnSeeConfirmPass.setImageResource(R.drawable.baseline_visibility_24)
                visiblePass = false
            }
        }


        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se requiere implementación
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Realizar la validación cuando se cambie el texto
                validatePasswordsMatch()
            }

            override fun afterTextChanged(s: Editable?) {
                // No se requiere implementación
            }
        })

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

    private fun validatePasswordsMatch() {
        val password = binding.etPass.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (password == confirmPassword) {
            //binding.passwordTextInputLayout.boxBackgroundColor = Color.GREEN
            //binding.confirmPasswordTextInputLayout.boxBackgroundColor = Color.GREEN
            binding.confirmPasswordTextInputLayout.boxStrokeColor = Color.GREEN
        } else {
            //binding.passwordTextInputLayout.boxBackgroundColor = Color.RED
            //binding.confirmPasswordTextInputLayout.boxBackgroundColor = Color.RED
            binding.confirmPasswordTextInputLayout.boxStrokeColor = Color.RED
        }
    }

}