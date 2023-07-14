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

class CategoryAdapterShow10ProductsForEachSubCategory(private val listSubCategorys: ArrayList<Int>,
                                                      private val context: Context,
                                                      private val idClient: Int ?= null,
                                                      private val onClickItem: (Int) -> Unit,
                                                      private val onProductClicked: (Int) -> Unit,
                                                      private val onTitleBrandClick: (Int) -> Unit): RecyclerView.Adapter<CategoryAdapterShow10ProductsForEachSubCategory.CategoryViewHolderShow10ProductsForEachSubCategory>() {



    class CategoryViewHolderShow10ProductsForEachSubCategory (view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebProductsRecyclerviewBinding.bind(view)

        fun bind(i: Int, context: Context, idClient: Int?, onClickItem: (Int) -> Unit, onProductClicked: (Int) -> Unit, onTitleBrandClick: (Int) -> Unit) {
            val dbMallweb = DbMallweb(context)
            val c = dbMallweb.queryForSubCategory(i)
            val adapter: ProductAdapter = if (idClient != null) {
                ProductAdapter(dbMallweb.queryForSubCategoryProducts(c.id), idClient, context, { onProductClicked(it) }, {})
            } else {
                ProductAdapter(dbMallweb.queryForSubCategoryProducts(c.id), onClickItem = {onProductClicked(it)}, onUnFavoriteClick = {})
            }
            binding.tvTitleBrand.text = c.name
            binding.tvTitleBrand.setOnClickListener { onTitleBrandClick(c.id) }
            if (binding.btnSeeAll.visibility == View.VISIBLE) {
                binding.btnSeeAll.setOnClickListener { onClickItem(c.id) }
            }
            binding.rvBrand.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            binding.rvBrand.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolderShow10ProductsForEachSubCategory {
        return CategoryViewHolderShow10ProductsForEachSubCategory(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_products_recyclerview, parent, false))
    }

    override fun getItemCount(): Int {
        return listSubCategorys.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolderShow10ProductsForEachSubCategory, position: Int) {
        holder.bind(listSubCategorys[position], context, idClient, onClickItem, onProductClicked, onTitleBrandClick)
    }
}