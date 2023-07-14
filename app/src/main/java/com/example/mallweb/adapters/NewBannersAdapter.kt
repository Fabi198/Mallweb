package com.example.mallweb.adapters


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebBannersBinding
import com.example.mallweb.entities.Banners
import com.squareup.picasso.Picasso

class NewBannersAdapter(private val onClickItem: (String) -> Unit): RecyclerView.Adapter<NewBannersAdapter.NewBannersViewHolder>() {

    private val rb1 = Banners(R.drawable.mallweb_banner_micuenta, "Mi Cuenta")
    private val rb2 = Banners(R.drawable.mallweb_banner_marcasdestacadas, "Marcas Destacadas")
    private val rb3 = Banners(R.drawable.mallweb_banner_comunidad, "Comunidad")
    private val rb4 = Banners(R.drawable.mallweb_banner_zonagamer, "Zona Gamer")
    private val rb5 = Banners(R.drawable.mallweb_banner_promociones, "Promociones")
    private val rb6 = Banners(R.drawable.mallweb_banner_corsair, "Corsair")
    private val rb7 = Banners(R.drawable.mallweb_banner_logitech, "Logitech")
    private val rb8 = Banners(R.drawable.mallweb_banner_seagate, "Seagate")
    private val rb9 = Banners(R.drawable.mallweb_banner_computacion, "Computación")
    private val rb10 = Banners(R.drawable.mallweb_banner_almacenamiento, "Almacenamiento")
    private val rb11 = Banners(R.drawable.mallweb_banner_componentespc, "Comp. PC")
    private val rb12 = Banners(R.drawable.mallweb_banner_perifericos, "Periféricos")
    private val rb13 = Banners(R.drawable.mallweb_banner_conectividad, "Conectividad")
    private val rb14 = Banners(R.drawable.mallweb_banner_impresion, "Impresión")
    private val rb15 = Banners(R.drawable.mallweb_banner_audioyvideo, "Audio y Video")

    private val listBanners: Array<Banners> = arrayOf(rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15)

    class NewBannersViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebBannersBinding.bind(view)

        fun bind(i: Banners, onClickItem: (String) -> Unit) {
            when(i.id) {
                R.drawable.mallweb_banner_computacion,
                R.drawable.mallweb_banner_almacenamiento,
                R.drawable.mallweb_banner_componentespc,
                R.drawable.mallweb_banner_conectividad,
                R.drawable.mallweb_banner_audioyvideo,
                R.drawable.mallweb_banner_perifericos,
                R.drawable.mallweb_banner_impresion -> {binding.ivBannerItem.setBackgroundColor(Color.BLACK)}
                R.drawable.mallweb_banner_logitech,
                R.drawable.mallweb_banner_seagate,
                R.drawable.mallweb_banner_corsair -> {binding.ivBannerItem.setBackgroundColor(Color.BLACK)}
            }
            Picasso.get().load(i.id).fit().into(binding.ivBannerItem)
            binding.ivBannerItem.setOnClickListener { onClickItem(i.tv) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewBannersViewHolder {
        return NewBannersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_banners, parent, false))
    }

    override fun getItemCount(): Int {
        return listBanners.size
    }

    override fun onBindViewHolder(holder: NewBannersViewHolder, position: Int) {
        holder.bind(listBanners[position], onClickItem)
    }
}