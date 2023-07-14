package com.example.mallweb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebProductsRecyclerviewBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Product

class FavoritesAdapter(
    private val listClientArray: ArrayList<Int>,
    private val context: Context,
    private val onClickItem: (Int) -> Unit,
    private val emptyList: (Int) -> Unit): RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {


    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebProductsRecyclerviewBinding.bind(view)
        lateinit var adapter: ProductAdapter

        fun bind(
            idClient: Int,
            context: Context,
            onClickItem: (Int) -> Unit,
            emptyList: (Int) -> Unit
        ) {
            val dbMallweb = DbMallweb(context)
            val listFavorites = dbMallweb.queryForFavorites(idClient)
            adapter = ProductAdapter(listFavorites, idClient, context, { onClickItem(it) }, { if (deleteItem(listFavorites, it) == 0) { emptyList(it) } })
            binding.btnSeeAll.visibility = View.INVISIBLE
            binding.tvTitleBrand.text = context.getString(R.string.mis_favoritos)
            binding.rvBrand.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.rvBrand.adapter = adapter
        }

        private fun deleteItem(listFavorites: ArrayList<Product>, position: Int): Int {
            listFavorites.removeAt(position)
            adapter.notifyItemRemoved(position)
            return listFavorites.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mallweb_products_recyclerview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listClientArray.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(listClientArray[position], context, onClickItem, emptyList)
    }
}