package com.example.mallweb.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebProductBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val listProduct: ArrayList<Product>,
                     private val idClient: Int ?= null,
                     private val context: Context ?= null,
                     private val onClickItem: (Int) -> Unit,
                     private val onUnFavoriteClick: (Int) -> Unit): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebProductBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(
            i: Product,
            idClient: Int?,
            context: Context?,
            onClickItem: (Int) -> Unit,
            onUnFavoriteClick: (Int) -> Unit
        ) {
            val s = "$"
            var full = false
            if (i.image != 0) {
                Picasso.get().load(i.image).fit().into(binding.ivPrintImage)
            }
            binding.tvPrintTitle.text = i.name
            binding.tvPrice.text = "U${s}S ${i.price}"
            binding.cvProduct.setOnClickListener { onClickItem(i.id) }
            if (idClient != null && context != null) {
                val dbMallweb = DbMallweb(context)
                if (dbMallweb.queryForFavorite(idClient, i.id)) {
                    binding.btnFavorites.setImageResource(R.drawable.baseline_star_24_green)
                    full = true
                } else {
                    binding.btnFavorites.setImageResource(R.drawable.baseline_star_border_24_green)
                }
                binding.btnFavorites.setOnClickListener {
                    full = if (full) {
                        dbMallweb.deleteFavorites(idClient, i.id)
                        binding.btnFavorites.setImageResource(R.drawable.baseline_star_border_24_green)
                        onUnFavoriteClick(adapterPosition)
                        false
                    } else {
                        dbMallweb.createFavorite(idClient, i.id)
                        binding.btnFavorites.setImageResource(R.drawable.baseline_star_24_green)
                        true
                    }
                }
            } else {
               binding.btnFavorites.setOnClickListener { onClickItem(i.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_product, parent, false))
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProduct[position], idClient, context, onClickItem, onUnFavoriteClick)
    }
}