package com.example.mallweb.fragmentsDrawerMenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentAccountBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.fragments.MyFavorites
import com.example.mallweb.fragments.MyOrders
import com.example.mallweb.fragments.MyPersonalData
import java.util.*

@Suppress("DEPRECATION")
class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private var flagData = true
    private var flagOrders = true
    private var flagOrdersCancelled = true
    private var flagMyFavorites = true
    private lateinit var dbMallweb: DbMallweb

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)



        binding.cvMyData.setOnClickListener {
            if (flagData) {
                if (!flagOrders) { setGoneMyOrders() }
                if (!flagOrdersCancelled) { setGoneMyCancelledOrders() }
                if (!flagMyFavorites) { setGoneMyFavorites() }
                setVisibleMyData()
            } else {
                setGoneMyData()
            }
        }

        binding.cvMyFavorites.setOnClickListener {
            if (flagMyFavorites) {
                if (!flagOrders) { setGoneMyOrders() }
                if (!flagOrdersCancelled) { setGoneMyCancelledOrders() }
                if (!flagData) { setGoneMyData() }
                setVisibleMyFavorites()
            } else {
                setGoneMyFavorites()
            }
        }


        binding.cvMyOrders.setOnClickListener {
            if (flagOrders) {
                if (!flagData) { setGoneMyData() }
                if (!flagOrdersCancelled) { setGoneMyCancelledOrders() }
                if (!flagMyFavorites) { setGoneMyFavorites() }
                setVisibleMyOrders()
            } else {
                setGoneMyOrders()
            }
        }


        binding.cvMyCancelledOrders.setOnClickListener {
            if (flagOrdersCancelled) {
                if (!flagData) { setGoneMyData() }
                if (!flagOrders) { setGoneMyOrders() }
                if (!flagMyFavorites) { setGoneMyFavorites() }
                setVisibleMyCancelledOrders()
            } else {
                setGoneMyCancelledOrders()
            }
        }

    }

    private fun setVisibleMyFavorites() {
        binding.cvMyFavorites.setCardBackgroundColor(resources.getColor(R.color.red))
        showMiniFragment(MyFavorites(), "MyFavorites")
        flagMyFavorites = false
    }

    private fun setGoneMyFavorites() {
        binding.cvMyFavorites.setCardBackgroundColor(resources.getColor(R.color.black))
        removeMiniFragment()
        flagMyFavorites = true
    }

    private fun setVisibleMyData() {
        binding.cvMyData.setCardBackgroundColor(resources.getColor(R.color.red))
        showMiniFragment(MyPersonalData(), "MyPersonalData")
        flagData = false
    }

    private fun setGoneMyData() {
        binding.cvMyData.setCardBackgroundColor(resources.getColor(R.color.black))
        removeMiniFragment()
        flagData = true
    }

    private fun setVisibleMyOrders() {
        dbMallweb = DbMallweb(requireContext())
        showMiniFragment(MyOrders(), "MyOrders", false)
        binding.cvMyOrders.setCardBackgroundColor(resources.getColor(R.color.red))
        flagOrders = false
    }

    private fun setGoneMyOrders() {
        binding.cvMyOrders.setCardBackgroundColor(resources.getColor(R.color.black))
        removeMiniFragment()
        flagOrders = true
    }

    private fun setVisibleMyCancelledOrders() {
        dbMallweb = DbMallweb(requireContext())
        showMiniFragment(MyOrders(), "MyOrders", true)
        binding.cvMyCancelledOrders.setCardBackgroundColor(resources.getColor(R.color.red))
        flagOrdersCancelled = false
    }

    private fun setGoneMyCancelledOrders() {
        binding.cvMyCancelledOrders.setCardBackgroundColor(resources.getColor(R.color.black))
        removeMiniFragment()
        flagOrdersCancelled = true
    }

    private fun removeMiniFragment() {
        val fragmentManager = childFragmentManager
        val fragment = fragmentManager.findFragmentById(R.id.containerSubFragmentPersonalData)
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (fragment != null) {
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()
        }

    }

    private fun showMiniFragment(
        fragment: Fragment,
        tag: String,
        abbandoned: Boolean? = null
    ) {
        val bundle = Bundle()
        if (abbandoned != null) { bundle.putBoolean(getString(R.string.are_abbandoned), abbandoned) }
        val idContainerParent = arguments?.getInt("ContainerID")
        if (idContainerParent != null) { bundle.putInt("ContainerParentID", idContainerParent) }
        fragment.arguments = bundle
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.containerSubFragmentPersonalData.id, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

}