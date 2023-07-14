package com.example.mallweb.objects

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.example.mallweb.R
import com.example.mallweb.db.DbMallweb

object ShoppingCartQuantity {

    fun setShoppingCartSize(activity: FragmentActivity, context: Context, idClient: Int) {
        activity as AppCompatActivity
        val dbMallweb = DbMallweb(context)
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbarMallwebHome)
        val tvCantShoppingCart = toolbar.findViewById<TextView>(R.id.tvCantShoppingCart)
        tvCantShoppingCart?.text = dbMallweb.queryForProductsOnCart(idClient).size.toString()
    }

}