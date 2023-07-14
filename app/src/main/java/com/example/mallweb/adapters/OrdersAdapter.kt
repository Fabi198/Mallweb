package com.example.mallweb.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebOrdersBinding
import com.example.mallweb.entities.dbEntities.Order
import com.example.mallweb.html.BodySeeOrder

class OrdersAdapter(private val listOrders: ArrayList<Order>, private val context: Context, private val onBackOrderClick: (Int) -> Unit): RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {



    class OrdersViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebOrdersBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(order: Order, context: Context, onBackOrderClick: (Int) -> Unit) {
            val dollar = "$"
            val htmlCode = BodySeeOrder.body(order, context)
            binding.webView.loadDataWithBaseURL(null, htmlCode, "text/html", "UTF-8", null)
            binding.tvNumOrder.text = order.numOrder.toString()
            binding.tvDate.text = order.date
            binding.tvTotal.text = "U${dollar}S ${String.format("%.2f", order.total)}"
            binding.tvState.text = order.state
            binding.tvShipping.text = order.shipping
            binding.btnSeeAllData.setOnClickListener {
                if (binding.tvSeeOrder.text.toString() == "Ver") {
                    binding.tvSeeOrder.text = "Cerrar"
                    binding.cvWebView.visibility = View.VISIBLE
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                    binding.cvWebView.startAnimation(anim)
                } else if (binding.tvSeeOrder.text.toString() == "Cerrar") {
                    binding.tvSeeOrder.text = "Ver"
                    val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                    binding.cvWebView.visibility = View.GONE
                    binding.cvWebView.startAnimation(anim)
                }
            }
            if (order.state == "Completado") { binding.btnBackToOrder.visibility = View.GONE }
            binding.btnBackToOrder.setOnClickListener {
                onBackOrderClick(order.numOrder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_orders, parent, false))
    }

    override fun getItemCount(): Int {
        return listOrders.size
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(listOrders[position], context, onBackOrderClick)
    }

}