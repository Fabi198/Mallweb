package com.example.mallweb.fragmentsSubAllCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentPrintBinding
import com.example.mallweb.adapters.CategoryAdapterShow10ProductsForEachSubCategory
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragments.ProductDetailFragment
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment
import com.example.mallweb.objects.Session.getUserID
import com.example.mallweb.objects.Session.sessionFromFragment
import java.util.ArrayList


class PrintFragment : Fragment(R.layout.fragment_print) {

    private lateinit var binding: FragmentPrintBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrintBinding.bind(view)
        val id = arguments?.getInt("ContainerID")

        if (id != null) {
            val idCArray = setArrayCategory(arrayOf(22, 23, 24, 25))
            val adapter: CategoryAdapterShow10ProductsForEachSubCategory = if (sessionFromFragment(requireActivity())) {
                CategoryAdapterShow10ProductsForEachSubCategory(idCArray, requireContext(), getUserID(requireContext(), requireActivity()), {
                    showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = it)
                }, {
                    showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                }, {
                    showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = it)
                })
            } else {
                CategoryAdapterShow10ProductsForEachSubCategory(idCArray, requireContext(), onClickItem = {
                    showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = it)
                }, onProductClicked = {
                    showFragmentFromFragment(requireActivity(), ProductDetailFragment(), "ProductDetailFragment", id, idProduct = it)
                }, onTitleBrandClick = {
                    showFragmentFromFragment(requireActivity(), SubCategoryFragment(), "SubCategoryFragment", id, idCategory = it)
                })
            }

            binding.rvPrinters.layoutManager = LinearLayoutManager(requireContext())
            binding.rvPrinters.adapter = adapter
        }


    }

    private fun setArrayCategory(i: Array<Int>): ArrayList<Int> {
        val idCArray = ArrayList<Int>()
        i.forEach { idCArray.add(it) }
        return idCArray
    }

}