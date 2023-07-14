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

class SubCategoryAdapterShowAllProductsOfEachBrand(
    private val listBrandsArray: ArrayList<Int>,
    private val idCategory: Int,
    private val context: Context,
    private val idClient: Int ?= null,
    private val onClickItem: (Int) -> Unit,
    private val onBrandClickItem: (Int) -> Unit
): RecyclerView.Adapter<SubCategoryAdapterShowAllProductsOfEachBrand.SubCategoryViewHolderShowAllProductsOfEachBrand>() {


    class SubCategoryViewHolderShowAllProductsOfEachBrand(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebProductsRecyclerviewBinding.bind(view)

        fun bind(
            iB: Int,
            iC: Int,
            context: Context,
            idClient: Int?,
            onClickItem: (Int) -> Unit,
            onBrandClickItem: (Int) -> Unit
        ) {
            val dbMallweb = DbMallweb(context)
            val b = dbMallweb.queryForBrand(iB)
            val adapter = if (idClient != null) {
                ProductAdapter(dbMallweb.queryForProductsByBrandAndCategory(iB, iC), idClient, context, onClickItem = { onClickItem(it) }, onUnFavoriteClick = {})
            } else {
                ProductAdapter(dbMallweb.queryForProductsByBrandAndCategory(iB, iC), onClickItem = { onClickItem(it) }, onUnFavoriteClick = {})
            }
            binding.btnSeeAll.visibility = View.INVISIBLE
            binding.tvTitleBrand.text = b.name
            binding.rvBrand.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.rvBrand.adapter = adapter
            binding.tvTitleBrand.setOnClickListener { onBrandClickItem(b.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolderShowAllProductsOfEachBrand {
        return SubCategoryViewHolderShowAllProductsOfEachBrand(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mallweb_products_recyclerview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listBrandsArray.size
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolderShowAllProductsOfEachBrand, position: Int) {
        holder.bind(listBrandsArray[position], idCategory, context, idClient, onClickItem, onBrandClickItem)
    }
}