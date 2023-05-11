package com.example.fedem.Comidas.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Common.Entities.ComidaEntity
import com.example.fedem.R
import com.example.fedem.databinding.ActivityItemEventoBinding

class ComidaAdapter(private var comidas: MutableList<ComidaEntity>, private var listener: OnClickListenerComida): RecyclerView.Adapter<ComidaAdapter.ViewHolder>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_evento, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comida = comidas.get(position)

        with(holder) {
            setListener(comida)
            binding.tvNombre.text=comida.nombre

            Glide.with(mContext)
                .load(comida.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.IvEvento)
        }
    }

    override fun getItemCount(): Int = comidas.size

    fun setComidas(comidas: MutableList<ComidaEntity>) {
        this.comidas = comidas
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ActivityItemEventoBinding.bind(view)

        fun setListener(comidaEntity: ComidaEntity) {
            binding.root.setOnClickListener {
                listener.onClick(comidaEntity)
            }
        }
    }
}
