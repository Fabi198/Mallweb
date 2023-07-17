package com.example.mallweb.objects

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.mallweb.R

object ShowFragment {

    fun showFragmentFromFragment(
        fragmentActivity: FragmentActivity,
        fragment: Fragment,
        tag: String,
        id: Int ?= null,
        nameCategory: String ?= null,
        idCArray: ArrayList<Int>?= null,
        idBrand: Int ?= null,
        idClient: Int ?= null,
        idProduct: Int ?= null,
        idCategory: Int ?= null,
        withShipping: Boolean? = null,
        postalCode: Int? = null,
        existingOrder: Int? = null,
        quantity: Int? = null,
        tagForAuth: String? = null
    ) {
        if (id != null) {
            val bundle = Bundle()
            bundle.putInt("ContainerID", id)
            if (nameCategory != null) { bundle.putString("NameCategory", nameCategory) }
            if (idCategory != null) { bundle.putInt("IDCategory", idCategory) }
            if (idCArray != null) { bundle.putIntegerArrayList("IDCategoryArray", idCArray) }
            if (idBrand != null) { bundle.putInt("IdBrand", idBrand) }
            if (idClient != null) { bundle.putInt("IdClient", idClient) }
            if (idProduct != null) { bundle.putInt("IDProduct", idProduct) }
            if (existingOrder != null) { bundle.putInt("existingOrder", existingOrder) }
            if (withShipping == true && postalCode != null) { bundle.putInt("postalCode", postalCode); bundle.putBoolean("withShipping", withShipping)} else if (withShipping == false){ bundle.putBoolean("withShipping", withShipping) }
            if (quantity != null) { bundle.putInt("quantity", quantity) }
            if (tagForAuth != null) { bundle.putString("tagForAuth", tagForAuth) }
            fragment.arguments = bundle
            fragmentActivity
                .supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.right_in,
                    R.anim.left_out,
                    R.anim.right_in,
                    R.anim.left_out)
                .replace(id, fragment, tag)
                .addToBackStack(tag)
                .commit()
        }
    }


}