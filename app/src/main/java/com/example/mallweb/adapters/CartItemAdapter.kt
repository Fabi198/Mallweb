package com.example.mallweb.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebShoppingCartBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.ShopCartItem

class CartItemAdapter(private val listItems: ArrayList<ShopCartItem>, private val context: Context, private val itemRemoval: (Int) -> Unit,
                      private val onChangedPrice: (Int) -> Unit,
                      private val openProductFragment: (Int) -> Unit): RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    class CartItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebShoppingCartBinding.bind(view)


        @SuppressLint("SetTextI18n")
        fun bind(
            i: ShopCartItem,
            context: Context,
            itemRemoval: (Int) -> Unit,
            onChangedPrice: (Int) -> Unit,
            openProductFragment: (Int) -> Unit
        ) {
            val dbMallweb = DbMallweb(context)
            binding.tvTitleItemShoppingCart.text = dbMallweb.queryForProduct(i.idProduct).name
            binding.selectorQuantity.setText(i.quantity)
            var price = dbMallweb.queryForProduct(i.idProduct).price * Integer.parseInt(binding.selectorQuantity.text)
            val dollar = "$"
            binding.tvPriceItemShoppingCart.text = "U${dollar}S ${String.format("%.2f", price)}"
            binding.tvTitleItemShoppingCart.setOnClickListener { openProductFragment(i.idProduct) }
            binding.selectorQuantity.imgAgregar.setOnClickListener {
                binding.selectorQuantity.plus()
                if (dbMallweb.refreshQuantityOfProductOnShopCart(binding.selectorQuantity.getText(), i.idProduct, i.idClient)) {
                    price = dbMallweb.queryForProduct(i.idProduct).price * binding.selectorQuantity.getText()
                    binding.tvPriceItemShoppingCart.text = "U${dollar}S ${String.format("%.2f", price)}"
                    onChangedPrice(i.idClient)
                } else {
                    binding.selectorQuantity.minus()
                    Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show()
                }
            }
            binding.selectorQuantity.imgRemover.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("¿Seguro desea eliminar este articulo del carrito?")
                builder.setPositiveButton("Aceptar") { _, _ ->
                    if (dbMallweb.refreshQuantityOfProductOnShopCart(binding.selectorQuantity.getText(), i.idProduct, i.idClient)) {
                        dbMallweb.deleteProductFromShopCart(i.idClient, i.idProduct)
                        val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                        itemView.startAnimation(anim)
                        itemView.visibility = View.GONE
                        itemRemoval(adapterPosition)
                        onChangedPrice(i.idClient)
                    } else {
                        binding.selectorQuantity.plus()
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show()
                    }
                }
                builder.setNegativeButton("Cancelar") { _, _ ->
                    binding.selectorQuantity.plus()
                    price = dbMallweb.queryForProduct(i.idProduct).price * binding.selectorQuantity.getText()
                    binding.tvPriceItemShoppingCart.text = "U${dollar}S ${String.format("%.2f", price)}"
                    onChangedPrice(i.idClient)
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                true }
            binding.selectorQuantity.imgRemover.setOnClickListener {
                binding.selectorQuantity.minus()
                if (binding.selectorQuantity.getText() == 0) {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("¿Seguro desea eliminar este articulo del carrito?")
                    builder.setPositiveButton("Aceptar") { _, _ ->
                        if (dbMallweb.refreshQuantityOfProductOnShopCart(binding.selectorQuantity.getText(), i.idProduct, i.idClient)) {
                            dbMallweb.deleteProductFromShopCart(i.idClient, i.idProduct)
                            val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                            itemView.startAnimation(anim)
                            itemView.visibility = View.GONE
                            itemRemoval(adapterPosition)
                            onChangedPrice(i.idClient)
                        } else {
                            binding.selectorQuantity.plus()
                            Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show()
                        }
                    }
                    builder.setNegativeButton("Cancelar") { _, _ ->
                        binding.selectorQuantity.plus()
                        price = dbMallweb.queryForProduct(i.idProduct).price * binding.selectorQuantity.getText()
                        binding.tvPriceItemShoppingCart.text = "U${dollar}S ${String.format("%.2f", price)}"
                        onChangedPrice(i.idClient)
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {
                    if (dbMallweb.refreshQuantityOfProductOnShopCart(binding.selectorQuantity.getText(), i.idProduct, i.idClient)) {
                        price = dbMallweb.queryForProduct(i.idProduct).price * binding.selectorQuantity.getText()
                        binding.tvPriceItemShoppingCart.text = "U${dollar}S ${String.format("%.2f", price)}"
                        onChangedPrice(i.idClient)
                    } else {
                        binding.selectorQuantity.plus()
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_shopping_cart, parent, false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(listItems[position], context, itemRemoval, onChangedPrice, openProductFragment)
    }


}