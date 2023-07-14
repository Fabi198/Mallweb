package com.example.mallweb.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mallweb.R
import com.example.mallweb.databinding.FragmentProductDetailBinding
import com.example.mallweb.auth.AuthFragment
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.fragments.ShowFragment.showFragmentFromFragment
import com.example.mallweb.fragmentsDrawerMenu.ShoppingCartFragmentStep1
import com.example.mallweb.objects.Session.getUserID
import com.example.mallweb.objects.Session.sessionFromFragment
import com.example.mallweb.objects.ShoppingCartQuantity.setShoppingCartSize
import com.squareup.picasso.Picasso


class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private lateinit var binding: FragmentProductDetailBinding


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)
        val dbMallweb = DbMallweb(requireContext())
        val idProduct = arguments?.getInt("IDProduct")
        val id = arguments?.getInt("ContainerID")
        setupSharedLink()
        setFavoriteButton(idProduct)
        setShoppingCardView()
        setShippingCalculator()

        if (idProduct != null) {
            val product = dbMallweb.queryForProduct(idProduct)
            val brand = dbMallweb.queryForBrand(product.idBrand)
            if (brand.image != 0) {
                Picasso.get().load(brand.image).fit().centerInside().into(binding.ivBrand)
                binding.ivBrand.setOnClickListener {
                    showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, brand.name, dbMallweb.queryForCategoryCant(brand.id), brand.id)
                }
            } else {
                binding.ivBrand.visibility = View.GONE
                binding.tvBrand.text = brand.name
                binding.tvBrand.visibility = View.VISIBLE
                binding.tvBrand.setOnClickListener {
                    showFragmentFromFragment(requireActivity(), CategoryFragment(), "CategoryFragment", id, brand.name, dbMallweb.queryForCategoryCant(brand.id), brand.id)
                }
            }
            if (product.image != 0) {
                Picasso.get().load(product.image).fit().into(binding.ivProductDetail)
            }
            binding.tvTitleProductDetail.text = product.name
            val dollar = "$"
            binding.tvListPrice.text = "U${dollar}S ${String.format("%.2f", product.price + ((product.price/100) * 5))}"
            binding.tvListPrice.apply { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
            binding.tvPriceProductDetail.text = "U${dollar}S ${String.format("%.2f", product.price)}"
        }




    }

    private fun setFavoriteButton(idProduct: Int?) {
        var full = false
        val dbMallweb = DbMallweb(requireContext())
        if (idProduct != null) {
            if (sessionFromFragment(requireActivity())) {
                full = if (dbMallweb.queryForFavorite(getUserID(requireContext(), requireActivity()), idProduct)) {
                    binding.btnFavorites.setImageResource(R.drawable.baseline_star_24_green)
                    true
                } else {
                    binding.btnFavorites.setImageResource(R.drawable.baseline_star_border_24_green)
                    false
                }
            } else {
                binding.btnFavorites.setImageResource(R.drawable.baseline_star_border_24_green)
            }

            binding.btnFavorites.setOnClickListener {
                if (sessionFromFragment(requireActivity())) {
                    full = if (full) {
                        dbMallweb.deleteFavorites(getUserID(requireContext(), requireActivity()), idProduct)
                        binding.btnFavorites.setImageResource(R.drawable.baseline_star_border_24_green)
                        false
                    } else {
                        dbMallweb.createFavorite(getUserID(requireContext(), requireActivity()), idProduct)
                        binding.btnFavorites.setImageResource(R.drawable.baseline_star_24_green)
                        true
                    }
                } else {
                    showFragmentFromFragment(requireActivity(), AuthFragment(), "AuthFragment", id, idProduct = idProduct, tagForAuth = "ProductDetailFragment")
                }
            }
        }
    }

    private fun setupSharedLink() {
        Picasso.get().load(R.drawable.mallweb_icono_shared_facebook).fit().into(binding.btnSharedFacebook)
        binding.btnSharedFacebook.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fwww.mallweb.com.ar"))) }
        Picasso.get().load(R.drawable.mallweb_icono_shared_whatsapp).fit().into(binding.btnSharedWhatsapp)
        binding.btnSharedWhatsapp.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?text=https://www.mallweb.com.ar"))) }
        Picasso.get().load(R.drawable.mallweb_icono_shared_twitter).fit().into(binding.btnSharedTwitter)
        binding.btnSharedTwitter.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?original_referer=https://www.mallweb.com.ar/discos-solidos/almacenamiento---disco-de-estado-solido/246927-disco-solido-ssd-240-gb-nvme-western-digital-green-sn350.html&tw_p=tweetbutton&url=https://www.mallweb.com.ar"))) }

    }

    private fun setShippingCalculator() {
        binding.cvShippingCalculator.setOnClickListener {  }
        binding.svMainProductDetail.setOnClickListener { if (binding.cvShippingCalculator.visibility == View.VISIBLE) {binding.cvShippingCalculator.visibility = View.GONE} }
        binding.btnCalculateShipping.setOnClickListener {
            binding.cvShippingCalculator.visibility = View.VISIBLE
            binding.btnCalculatorShipping.setOnClickListener {
                if (binding.tvDisplayed4.text.toString().isNotEmpty()) {
                    binding.tvDisplayed4.text.clear()
                    binding.btnCalculatorShipping.visibility = View.GONE
                    binding.tvDisplayed4.visibility = View.GONE
                    binding.clDisplayedResult.visibility = View.VISIBLE
                    binding.btnContinue.visibility = View.VISIBLE
                    binding.btnContinue.setOnClickListener {
                        binding.clDisplayedResult.visibility = View.GONE
                        binding.btnContinue.visibility = View.GONE
                        binding.tvDisplayed4.visibility = View.VISIBLE
                        binding.btnCalculatorShipping.visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.btnCloseCVShippingCalculator.setOnClickListener { binding.cvShippingCalculator.visibility = View.GONE }
        binding.btnCloseCVShippingCalculator2.setOnClickListener {
            binding.clDisplayedResult.visibility = View.GONE
            binding.btnContinue.visibility = View.GONE
            binding.tvDisplayed4.visibility = View.VISIBLE
            binding.btnCalculatorShipping.visibility = View.VISIBLE
        }
    }

    private fun setShoppingCardView() {
        binding.cvShoppingCardView.setOnClickListener {  }
        val dbMallweb = DbMallweb(requireContext())
        val idProduct = arguments?.getInt("IDProduct")
        val id = arguments?.getInt("ContainerID")
        binding.svMainProductDetail.setOnClickListener { if (binding.cvShoppingCardView.visibility == View.VISIBLE) {binding.cvShoppingCardView.visibility = View.GONE} }
        binding.btnShop.setOnClickListener {
            if (sessionFromFragment(requireActivity())) {
                binding.pbBtnShop.visibility = View.VISIBLE
                if (idProduct != null) {
                    if (getUserID(requireContext(), requireActivity()) > 0) {
                        if (Integer.parseInt(binding.cantProductDetail.text.toString()) <= dbMallweb.queryForProduct(idProduct).stock) {
                            val result = dbMallweb.addProductToShoppingCart(getUserID(requireContext(), requireActivity()), idProduct, Integer.parseInt(binding.cantProductDetail.text.toString()))
                            if (result) {
                                setShoppingCartSize(requireActivity(), requireContext(), getUserID(requireContext(), requireActivity()))
                                binding.pbBtnShop.visibility = View.GONE
                                binding.cvShoppingCardView.visibility = View.VISIBLE
                                binding.btnKeepShopping.setOnClickListener { binding.cvShoppingCardView.visibility = View.GONE }
                                binding.btnGoToShoppingCartFragment.setOnClickListener { showFragmentFromFragment(requireActivity(), ShoppingCartFragmentStep1(), "ShoppingCartFragmentStep1", id, idClient = getUserID(requireContext(), requireActivity())) }
                            } else {
                                binding.pbBtnShop.visibility = View.GONE
                            }
                        } else {
                            showAlertErrorQuantity("Cantidad insuficiente, por favor disminuyala")
                            binding.pbBtnShop.visibility = View.GONE
                        }
                    } else {
                        showAlertErrorQuantity("Cliente no encontrado, intente cerrar sesión y registrarse nuevamente")
                        binding.pbBtnShop.visibility = View.GONE
                    }
                } else {
                    showAlertErrorQuantity("Error en la base de datos")
                    binding.pbBtnShop.visibility = View.GONE
                }
            } else {
                showFragmentFromFragment(requireActivity(), AuthFragment(), "AuthFragment", id, idProduct = idProduct, quantity = Integer.parseInt(binding.cantProductDetail.text.toString()), tagForAuth = "ProductDetailFragment")
            }

        }
        binding.btnCloseCV.setOnClickListener { binding.cvShoppingCardView.visibility = View.GONE }
    }

    private fun showAlertErrorQuantity(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}