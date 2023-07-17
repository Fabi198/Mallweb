package com.example.mallweb.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mallweb.R
import com.example.mallweb.adapters.ProductAdapter
import com.example.mallweb.adapters.SubCategoryAdapterShowAllProductsOfEachBrand
import com.example.mallweb.databinding.FragmentSubCategoryBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.objects.Session.getUserID
import com.example.mallweb.objects.Session.sessionFromFragment
import com.example.mallweb.objects.ShowFragment.showFragmentFromFragment


class SubCategoryFragment : Fragment(R.layout.fragment_sub_category) {

    private lateinit var binding: FragmentSubCategoryBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubCategoryBinding.bind(view)
        val id = arguments?.getInt("ContainerID")
        val idCategory = arguments?.getInt("IDCategory")
        val stringSearch = arguments?.getString("Search")
        val name = arguments?.getString("NameCategory")


        if (idCategory != null && id != null) {
            val dbMallweb = DbMallweb(requireContext())
            val idBrandArray = dbMallweb.queryForBrandCant(idCategory)
            val adapter = if (sessionFromFragment(requireActivity())) {
                SubCategoryAdapterShowAllProductsOfEachBrand(idBrandArray, idCategory, requireContext(), getUserID(requireContext(), requireActivity()),
                    {
                        showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                    },
                    {
                        val brand = dbMallweb.queryForBrand(it)
                        showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, brand.name, dbMallweb.queryForCategoryCant(brand.id), brand.id)
                    }
                )
            } else {
                SubCategoryAdapterShowAllProductsOfEachBrand(idBrandArray, idCategory, requireContext(),
                    onClickItem = {
                        showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                    },
                    onBrandClickItem = {
                        val brand = dbMallweb.queryForBrand(it)
                        showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, brand.name, dbMallweb.queryForCategoryCant(brand.id), brand.id)
                    }
                )
            }
            binding.rvBrands.layoutManager = LinearLayoutManager(requireContext())
            binding.rvBrands.adapter = adapter
        }

        if (stringSearch != null && id != null) {
            val dbMallweb = DbMallweb(requireContext())
            val adapter: ProductAdapter = if (sessionFromFragment(requireActivity())) {
                ProductAdapter(dbMallweb.queryBasicForGlobalSearch(stringSearch.lowercase()), getUserID(requireContext(), requireActivity()), requireContext(), {
                    showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                }, {})
            } else {
                ProductAdapter(dbMallweb.queryBasicForGlobalSearch(stringSearch.lowercase()), onClickItem = {
                    showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                }, onUnFavoriteClick = {})
            }
            binding.tvTitleBrand.text = "'$name'"
            binding.rvBrands.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvBrands.adapter = adapter
        }

    }

}