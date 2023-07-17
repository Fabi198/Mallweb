package com.example.mallweb.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mallweb.R
import com.example.mallweb.adapters.OrdersAdapter
import com.example.mallweb.alarms.AlarmAbbandonedOrder.setSecondAlarm
import com.example.mallweb.databinding.FragmentMyOrdersBinding
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.fragmentsDrawerMenu.ShoppingCartFragmentStep1
import com.example.mallweb.objects.ShowFragment.showFragmentFromFragment


class MyOrders : Fragment(R.layout.fragment_my_orders) {

    private lateinit var binding: FragmentMyOrdersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyOrdersBinding.bind(view)
        val containerIDParent = arguments?.getInt("ContainerParentID")
        val abbandoned = arguments?.getBoolean(getString(R.string.are_abbandoned))

        val dbMallweb = DbMallweb(requireContext())
        val prefs: SharedPreferences = requireActivity().getSharedPreferences("MY PREF", AppCompatActivity.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if (email != null && abbandoned != null && containerIDParent != null) {
            val client = dbMallweb.queryForClient(email)
            binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
            if (abbandoned) {
                if (dbMallweb.queryForOrders(client.id, getString(R.string.abandonado)).size > 0) {
                    binding.rvOrders.adapter = OrdersAdapter(dbMallweb.queryForOrders(client.id, getString(R.string.abandonado)), requireContext()) {
                        dbMallweb.deleteAllProductsOnShopCart(client.id)
                        dbMallweb.putDetailsBackToCart(it, client.id)
                        dbMallweb.editStateOrder(it, getString(R.string.en_curso))
                        setSecondAlarm(it, requireContext())
                        showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep1(), "ShoppingCartFragmentStep1", containerIDParent, existingOrder = it, idClient = client.id)
                    }
                } else {
                    binding.clOrders.visibility = View.GONE
                    binding.tvNoOrders.text = getString(R.string.no_hay_pedidos_abandonados)
                    binding.tvNoOrders.visibility = View.VISIBLE
                }
            } else {
                if (dbMallweb.queryForOrders(client.id, getString(R.string.completado)).size > 0) {
                    binding.rvOrders.adapter = OrdersAdapter(dbMallweb.queryForOrders(client.id, getString(R.string.completado)), requireContext()) {}
                } else {
                    binding.clOrders.visibility = View.GONE
                    binding.tvNoOrders.text = getString(R.string.no_hay_pedidos)
                    binding.tvNoOrders.visibility = View.VISIBLE
                }
            }

        }
    }

}