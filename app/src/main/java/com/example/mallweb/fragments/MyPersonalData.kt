package com.example.mallweb.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentMyPersonalDataBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.objects.Refresh.refresh
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*


@Suppress("DEPRECATION")
class MyPersonalData : Fragment(R.layout.fragment_my_personal_data) {

    private lateinit var binding: FragmentMyPersonalDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPersonalDataBinding.bind(view)


        setClientData()

    }

    private fun setClientData() {
        val dbMallweb = DbMallweb(requireContext())
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if (email != null) {
            val client = dbMallweb.queryForClient(email)
            binding.idclient.text = client.id.toString()
            binding.emailClient.text = client.email
            binding.nameClient.setText(client.name)
            binding.dateBirthClient.setText(client.birthday)
            binding.codAreaNumber.setText(client.codArea)
            binding.numberCellphoneMallwebClient.setText(client.numCelular)
            binding.dniClientMallweb.setText(client.dni)
            binding.cuitClientMallweb.setText(client.cuit)
            binding.wantABillClientMallweb.setText(client.wantABill)
            if (dbMallweb.queryForShippingAddress(client.id).idClient > 0) {
                with(dbMallweb.queryForShippingAddress(client.id)) {
                    binding.streetShippingClientMallweb.text = street
                    binding.heightShippingClientMallweb.text = number
                    binding.floorShippingClientMallweb.text = floor
                    binding.doorShippingClientMallweb.text = door
                    binding.postalCodeShippingClientMallweb.text = postalCode
                    binding.provinceShippingClientMallweb.text = province
                    binding.localityShippingClientMallweb.text = locality
                }
            }
        }




        binding.dateBirthClient.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) { hideKeyboard(); openCalendar(); binding.dateBirthClient.setOnClickListener { openCalendar() } } }
        binding.wantABillClientMallweb.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) { hideKeyboard(); setBillBoolean(); binding.wantABillClientMallweb.setOnClickListener { setBillBoolean() } } }
        binding.btnActProfile.setOnClickListener {
            if (areEmpty()) {
                if (dbMallweb.editClient(
                        Integer.parseInt(binding.idclient.text.toString()),
                        binding.nameClient.text.toString(),
                        binding.dateBirthClient.text.toString(),
                        binding.codAreaNumber.text.toString(),
                        binding.numberCellphoneMallwebClient.text.toString(),
                        binding.dniClientMallweb.text.toString(),
                        binding.cuitClientMallweb.text.toString(),
                        binding.wantABillClientMallweb.text.toString()
                    )) {
                    showAlertSuccess()
                } else {
                    showAlertError()
                }
            } else {
                Toast.makeText(requireContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnEraseProfile.setOnClickListener { showAlertEraseAccount() }
    }

    private fun areEmpty(): Boolean { return (binding.idclient.text.toString().isNotEmpty() && binding.nameClient.text.toString().isNotEmpty() && binding.dateBirthClient.text.toString().isNotEmpty() && binding.codAreaNumber.text.toString().isNotEmpty() && binding.numberCellphoneMallwebClient.text.toString().isNotEmpty() && binding.dniClientMallweb.text.toString().isNotEmpty() && binding.cuitClientMallweb.text.toString().isNotEmpty() && binding.wantABillClientMallweb.text.toString().isNotEmpty()) }

    private fun setBillBoolean() {
        val si = resources.getString(R.string.si)
        val no = resources.getString(R.string.no)
        val options = arrayOf(si, no)
        var defaultPosition = 0
        val builderSingle = AlertDialog.Builder(requireContext())
        builderSingle.setPositiveButton(getString(android.R.string.ok)) {dialog, _ -> dialog.dismiss()}
        builderSingle.setSingleChoiceItems(options, defaultPosition) {_, witch ->
            defaultPosition = witch
            binding.wantABillClientMallweb.setText(options[witch])
        }
        builderSingle.show()

    }

    private fun openCalendar() {
        val cal: Calendar = Calendar.getInstance()
        val yearGetter = cal.get(Calendar.YEAR)
        val monthGetter = cal.get(Calendar.MONTH)
        val dayGetter = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), 0,
            { _, year, month, dayOfMonth ->
                lateinit var fecha: String
                if ((month+1) in 0..9 && dayOfMonth in 10..31) {
                    fecha = "$year-0${month+1}-$dayOfMonth"
                } else if ((month+1) in 0..9 && dayOfMonth in 0..9) {
                    fecha = "$year-0${month+1}-0$dayOfMonth"
                } else if ((month+1) in 10..12 && dayOfMonth in 0..9) {
                    fecha = "$year-${month+1}-0$dayOfMonth"
                } else if ((month+1) in 10..12 && dayOfMonth in 10..31) {
                    fecha = "$year-${month+1}-$dayOfMonth"
                }
                binding.dateBirthClient.setText(fecha)
            }, yearGetter, monthGetter, dayGetter)
        dpd.show()
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.svMyPersonalData.windowToken, 0)
    }

    private fun showAlertEraseAccount() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¿Desea eliminar su cuenta y todos sus datos?")
        builder.setPositiveButton("Eliminar"){ _, _ -> getProvider() }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun getProvider() {
        // Consigue el usuario actual
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Consigue el provider con el que se registro el usuario
            val providers = user.providerData
            for (userInfo in providers) {
                when(userInfo.providerId) {
                    GoogleAuthProvider.PROVIDER_ID -> showGoogleDialog()
                    EmailAuthProvider.PROVIDER_ID -> showEmailDialog()
                }
            }
        }
    }




    private fun showEmailDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Valide sus datos")

        // Inflar el diseño personalizado
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_auth_erase_account, null)
        builder.setView(dialogLayout)

        // Configurar los botones
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            val editText2 = dialogLayout.findViewById<EditText>(R.id.etPassword)
            val password = editText2.text.toString()

            // Reautentica al usuario utilizando su contraseña
            val user = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider.getCredential(user?.email ?: "", password)
            user?.reauthenticate(credential)
                ?.addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // La reautenticación fue exitosa, puedes eliminar la cuenta del usuario
                        eraseAccount(user)
                    } else {
                        // La reautenticación falló, muestra un mensaje de error
                        Toast.makeText(requireContext(), reauthTask.exception?.message ?: "Ocurrio un error al validar la cuenta", Toast.LENGTH_SHORT).show()
                    }
                }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        // Mostrar el dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun showGoogleDialog() {
        // Crea las opciones de autenticación de Google
        val googleOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Crea el cliente de inicio de sesión de Google
        val googleClient = GoogleSignIn.getClient(requireContext(), googleOptions)

        // Inicia sesión con Google nuevamente
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, 100)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val user = FirebaseAuth.getInstance().currentUser
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    // Reautentica al usuario con Google
                    user?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            // La reautenticación fue exitosa, se puede eliminar el usuario
                            eraseAccount(user)
                        } else {
                            Toast.makeText(requireContext(), reauthTask.exception?.message ?: "Ocurrio un error validando el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun eraseAccount(currentUser: FirebaseUser) {
        currentUser.delete()
            .addOnCompleteListener { deleteTask ->
                if (deleteTask.isSuccessful) {
                    val dbMallweb = DbMallweb(requireContext())
                    val prefs: SharedPreferences = requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
                    dbMallweb.deleteClient(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteAllProductsOnShopCart(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteAllFavorites(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteAllDetails(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteAllOrders(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteBillAddress(Integer.parseInt(binding.idclient.text.toString()))
                    dbMallweb.deleteShippingAddress(Integer.parseInt(binding.idclient.text.toString()))
                    val prefsEd = prefs.edit()
                    prefsEd.clear()
                    prefsEd.apply()
                    refresh(requireActivity(), requireContext())
                } else {
                    val exception = deleteTask.exception
                    Toast.makeText(requireContext(), exception?.message ?: "Ocurrio un error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun showAlertSuccess() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Sus datos han sido actualizados")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertError() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Sus datos no han sido actualizados")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}