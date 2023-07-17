package com.example.mallweb.auth

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mallweb.MallWeb
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentAuthBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.objects.Session.getUserID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@Suppress("DEPRECATION")
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding
    private val provider = ProviderType()
    private lateinit var googleClient: GoogleSignInClient
    private var visiblePass = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        val id2 = arguments?.getInt("ContainerID")


        val analytics = FirebaseAnalytics.getInstance(requireContext())
        val bundle = Bundle()
        bundle.putString("message", "Integracion de firebase completa")
        analytics.logEvent("InitScreen", bundle)

        setup(id2)
    }

    private fun setup(id2: Int?) {

        binding.btnSeePass.setOnClickListener {
            if (!visiblePass) {
                binding.etPass.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnSeePass.setImageResource(R.drawable.baseline_visibility_off_24)
                visiblePass = true
            } else {
                binding.etPass.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                binding.btnSeePass.setImageResource(R.drawable.baseline_visibility_24)
                visiblePass = false
            }
        }

        binding.btnReg.setOnClickListener {
            if (id2 != null) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.right_in,
                        R.anim.left_out,
                        R.anim.right_in,
                        R.anim.left_out
                    )
                    .replace(id2, RegisterFragment(), "RegisterFragment")
                    .apply { arguments?.putInt("ContainerID", id) }
                    .addToBackStack("RegisterFragment")
                    .commit()
            }
        }

        binding.btnBasic.setOnClickListener {
            if (binding.etEmail.text.isNotEmpty() && binding.etPass.text.isNotEmpty()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                binding.pbAuth.visibility = View.VISIBLE
                allGone()
                val providerName = provider.BASIC
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            saveData(it.result?.user?.email ?: "", providerName)
                            onSuccessAuth()
                        } else {
                            binding.pbAuth.visibility = View.GONE
                            allVisible()
                            showAlert()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Debe completar ambos campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoogle.setOnClickListener {
            allGone()
            binding.pbAuth.visibility = View.VISIBLE
            val googleConf = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleClient = GoogleSignIn.getClient(requireContext(), googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, 100)
        }


        binding.btnLostPassword.setOnClickListener {
            if (id2 != null) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.right_in,
                        R.anim.left_out,
                        R.anim.right_in,
                        R.anim.left_out
                    )
                    .replace(id2, RecoverPassFragment(), "Recover Pass Fragment")
                    .apply { arguments?.putInt("ContainerID", id) }
                    .addToBackStack("Recover Pass Fragment")
                    .commit()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                val providerName = provider.GOOGLE
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val dbMallweb = DbMallweb(requireContext())
                                if (account.email?.let { it1 -> dbMallweb.queryForClient(it1).email } == account.email) {
                                    saveData(account.email ?: "", providerName)
                                    onSuccessAuth()
                                } else {
                                    if (dbMallweb.createBasicClient(account.email ?: "", account.displayName ?: "", "", "") > 0) {
                                        saveData(account.email ?: "", providerName)
                                        onSuccessAuth()
                                    } else {
                                        binding.pbAuth.visibility = View.GONE
                                        allVisible()
                                        googleClient.signOut()
                                    }
                                }
                            } else {
                                binding.pbAuth.visibility = View.GONE
                                allVisible()
                                showAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                binding.pbAuth.visibility = View.GONE
                allVisible()
                showAlert2()
            }

        }
    }

    private fun onSuccessAuth() {
        val idProduct = arguments?.getInt("IDProduct")
        val quantity = arguments?.getInt("quantity")
        val tag = arguments?.getString("tagForAuth")
        val idClient = getUserID(requireContext(), requireActivity())
        var task = ""
        val dbMallweb = DbMallweb(requireContext())
        when (tag) {
            "AccountFragment" -> {
                task = "Open Account Fragment"
            }
            "ProductDetailFragment" -> {
                if (idProduct != null) {
                    task = if (quantity != null && quantity > 0) {
                        dbMallweb.addProductToShoppingCart(
                            idClient,
                            idProduct,
                            quantity
                        )
                        "Open Shopping Cart Fragment Step 1"
                    } else {
                        dbMallweb.createFavorite(idClient, idProduct)
                        "Open Product Detail Fragment"
                    }
                }
            }
            "ShoppingCartFragmentStep1" -> {
                task = "Open Shopping Cart Fragment Step 1"
            }
            else -> {
                if (idProduct != null) {
                    dbMallweb.createFavorite(idClient, idProduct)
                }
            }
        }
        val intent = Intent(
            requireContext(),
            MallWeb::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("tag", tag)
        intent.putExtra("idClient", idClient)
        intent.putExtra("idProduct", idProduct)
        intent.putExtra("quantity", quantity)
        intent.putExtra("task", task)
        startActivity(intent)
        requireActivity().overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showAlert2() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario, la cuenta es nula")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun saveData(email: String, providerName: String) {
        val prefs: SharedPreferences =
            requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        val prefsEd = prefs.edit()
        prefsEd.putString("email", email)
        prefsEd.putString("provider", providerName)
        prefsEd.apply()
    }

    private fun allVisible() {
        binding.tvFastAuth.visibility = View.VISIBLE
        binding.tvManualAuth.visibility = View.VISIBLE
        binding.btnGoogle.visibility = View.VISIBLE
        binding.llManualAuth.visibility = View.VISIBLE
        binding.btnBasic.visibility = View.VISIBLE
        binding.llBtnRegister.visibility = View.VISIBLE
    }

    private fun allGone() {
        binding.tvFastAuth.visibility = View.GONE
        binding.tvManualAuth.visibility = View.GONE
        binding.btnGoogle.visibility = View.GONE
        binding.llManualAuth.visibility = View.GONE
        binding.btnBasic.visibility = View.GONE
        binding.llBtnRegister.visibility = View.GONE
    }

}