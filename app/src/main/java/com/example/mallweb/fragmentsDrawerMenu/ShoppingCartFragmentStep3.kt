package com.example.mallweb.fragmentsDrawerMenu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentShoppingCartStep3Binding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Client
import com.example.mallweb.entities.dbEntities.Order
import com.example.mallweb.html.*
import com.example.mallweb.objects.Refresh.refresh
import com.example.mallweb.objects.Session
import com.example.mallweb.objects.ShoppingCartQuantity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.io.Serializable
import java.util.*


class ShoppingCartFragmentStep3: Fragment(R.layout.fragment_shopping_cart_step3) {

    private lateinit var binding: FragmentShoppingCartStep3Binding
    private lateinit var dbMallweb: DbMallweb
    private val dollar = "$"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShoppingCartStep3Binding.bind(view)
        dbMallweb = DbMallweb(requireContext())

        val prefs: SharedPreferences = requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        with(prefs.getString("email", null)?.let { dbMallweb.queryForClient(it) }) { this?.let { setOrderCV(it) } }
        setShippingVisibility(arguments?.getInt("postalCode"), arguments?.getBoolean("withShipping"))
        setPaymentCardPlanVisibility()

    }

    @SuppressLint("SetTextI18n")
    private fun setOrderCV(client: Client) {
        dbMallweb = DbMallweb(requireContext())
        val address = dbMallweb.queryForBillAddress(client.id)
        var order = Order()
        val existingOrder = arguments?.getInt(getString(R.string.existingOrder))
        if (existingOrder != null) {
            if (existingOrder > 0) {
                order = dbMallweb.queryForOrder(existingOrder)
            } else if (existingOrder == 0) {
                order = dbMallweb.queryForLastOrder(client.id)
            }
        } else {
            order = dbMallweb.queryForLastOrder(client.id)
        }
        binding.cvOrderTitle.text = "${getString(R.string.order)} #${order.numOrder}"
        binding.cvOrderSubtotalDisplayed.text = "U${dollar}S ${String.format("%.2f", order.total)}"
        binding.tvCabalDisplayed.text = "Ahora 12 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*41.9)) / 12)} (PTF182941.3595)\nAhora 18 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*63.7)) / 18)} (PTF212374.2585)\nAhora 24 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*81)) / 24)} (PTF244197.45)"
        binding.tvVisaDisplayed.text = "Ahora 12 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*41.9)) / 12)} (PTF182941.3595)\nAhora 18 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*63.7)) / 18)} (PTF212374.2585)\nAhora 24 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*81)) / 24)} (PTF244197.45)"
        binding.tvMasterDisplayed.text = "Ahora 12 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*41.9)) / 12)} (PTF182941.3595)\nAhora 18 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*63.7)) / 18)} (PTF212374.2585)\nAhora 24 de U${dollar}S ${String.format("%.2f", (order.total + ((order.total/100)*81)) / 24)} (PTF244197.45)"
        binding.cvMercadoPagoPaymentTotalDisplayed.text = "U${dollar}S ${String.format("%.2f", order.total)}"
        binding.cvTransferPaymentTotalDisplayed.text = "U${dollar}S ${String.format("%.2f", order.total - ((order.total/100)*5))}"
        binding.cvCashAtLocalPaymentTotalDisplayed.text = "U${dollar}S ${String.format("%.2f", order.total - ((order.total/100)*5))}"

        binding.cvCashAtLocalPayment.setOnClickListener { showAlertConfirmShop(client.id, order, 1,hashMapOf("to" to client.email, "message" to hashMapOf("subject" to "Gracias por tu pedido [${order.numOrder}]", "html" to BodyCashAtLocal.body(client, address, order, requireContext())))) }
        binding.cvTransferPayment.setOnClickListener { if (binding.cvCashAtLocalPayment.visibility == View.GONE) { showAlertConfirmShop(client.id, order, 2, hashMapOf("to" to client.email, "message" to hashMapOf("subject" to "Gracias por tu pedido [${order.numOrder}]", "html" to BodyTransferPaymentShipping.body(client, address, dbMallweb.queryForShippingAddress(client.id), order, requireContext())))) } else if (binding.cvCashAtLocalPayment.visibility == View.VISIBLE) { showAlertConfirmShop(client.id, order, 2, hashMapOf("to" to client.email, "message" to hashMapOf("subject" to "Gracias por tu pedido [${order.numOrder}]", "html" to BodyTransferPayment.body(client, address, order, requireContext())))) } }
        binding.cvMercadoPagoPayment.setOnClickListener { if (binding.cvCashAtLocalPayment.visibility == View.GONE) { showAlertConfirmShop(client.id, order, 3, hashMapOf("to" to client.email, "message" to hashMapOf("subject" to "Gracias por tu pedido [${order.numOrder}]", "html" to BodyMercadoPagoPaymentShipping.body(client, address, dbMallweb.queryForShippingAddress(client.id), order, requireContext())))) } else if (binding.cvCashAtLocalPayment.visibility == View.VISIBLE) { showAlertConfirmShop(client.id, order, 3, hashMapOf("to" to client.email, "message" to hashMapOf("subject" to "Gracias por tu pedido [${order.numOrder}]", "html" to BodyMercadoPagoPayment.body(client, address, order, requireContext())))) } }
    }





    private fun setPaymentCardPlanVisibility() {
        binding.btnSeePaymentPlan.setOnClickListener { paymentCardPlanVisible() }
        binding.btnCloseCV.setOnClickListener { paymentCardPlanGone() }
        binding.btnCloseCV2.setOnClickListener { paymentCardPlanGone() }
    }

    private fun paymentCardPlanVisible() {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.roll_down_payment_plans)
        with(binding.cvPaymentCardPlan) { startAnimation(anim); visibility = View.VISIBLE; anim.setAnimationListener(object:
            Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {} }) }
    }

    private fun paymentCardPlanGone() {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.roll_up_payment_plans)
        with(binding.cvPaymentCardPlan) { startAnimation(anim); anim.setAnimationListener(object:
            Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) { visibility = View.GONE }
            override fun onAnimationRepeat(animation: Animation?) {} }) } }

    private fun setShippingVisibility(postalCodeFromBundle: Int?, withShipping: Boolean?) { if (postalCodeFromBundle != null && withShipping != null) { if ((postalCodeFromBundle > 0) && withShipping) { binding.cvCashAtLocalPayment.visibility = View.GONE } else { binding.cvCashAtLocalPayment.visibility = View.VISIBLE } } }

    private fun showAlertConfirmShop(idClient: Int, order: Order, numPayment: Int, hashMapOf: HashMap<String, Serializable>) {
        val dbFireBase = Firebase.firestore
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¿Desea confirmar la compra?")
        builder.setPositiveButton("Comprar"){ _, _ -> dbFireBase.collection("email").add(hashMapOf); onSuccessBuy(idClient, order, numPayment) }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun onSuccessBuy(idClient: Int, order: Order, numPayment: Int) {
        dbMallweb = DbMallweb(requireContext())
        dbMallweb.deleteAllProductsOnShopCart(idClient)
        ShoppingCartQuantity.setShoppingCartSize(requireActivity(), requireContext(), Session.getUserID(requireContext(), requireActivity()))
        if (numPayment == 1 || numPayment == 2) { dbMallweb.editTotalOrder(order.numOrder, order.total - ((order.total/100)*5)) }
        dbMallweb.editStateOrder(order.numOrder, getString(R.string.completado))
        dbMallweb.editPayMethodOrder(order.numOrder, numPayment)
        refresh(requireActivity(), requireContext())
    }
}