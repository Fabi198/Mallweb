package com.example.mallweb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.R
import com.example.mallweb.databinding.ItemMallwebRoundBottomsBinding
import com.example.mallweb.entities.RoundBottoms
import com.squareup.picasso.Picasso

class RoundBottomsAdapter(private val onClickItem: (String) -> Unit): RecyclerView.Adapter<RoundBottomsAdapter.RoundBottomsViewHolder>() {

    private val rb1 = RoundBottoms(R.drawable.mallweb_computacion_round_logo, "Computación")
    private val rb2 = RoundBottoms(R.drawable.mallweb_componentespc_round_logo, "Comp. PC")
    private val rb3 = RoundBottoms(R.drawable.mallweb_almacenamiento_round_logo, "Storage")
    private val rb4 = RoundBottoms(R.drawable.mallweb_perifericos_round_logo, "Periféricos")
    private val rb5 = RoundBottoms(R.drawable.mallweb_conectividad_round_logo, "Conectividad")
    private val rb6 = RoundBottoms(R.drawable.mallweb_impresion_round_logo, "Impresión")
    private val rb7 = RoundBottoms(R.drawable.mallweb_audioyvideo_round_logo, "Audio y Video")
    private val rb8 = RoundBottoms(R.drawable.mallweb_zonagamer_round_logo, "Zona Gamer")


    private val listBottoms: Array<RoundBottoms> = arrayOf(rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8)

    class RoundBottomsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemMallwebRoundBottomsBinding.bind(view)

        fun bind(i: RoundBottoms, onClickItem: (String) -> Unit) {
            Picasso.get().load(i.iv).fit().into(binding.ivRoundBottom)
            binding.tvRoundBottom.text = i.tv
            itemView.setOnClickListener { onClickItem(i.tv) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundBottomsViewHolder {
        return RoundBottomsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_mallweb_round_bottoms, parent, false))
    }

    override fun getItemCount(): Int {
        return listBottoms.size
    }

    override fun onBindViewHolder(holder: RoundBottomsViewHolder, position: Int) {
        holder.bind(listBottoms[position], onClickItem)
    }
}