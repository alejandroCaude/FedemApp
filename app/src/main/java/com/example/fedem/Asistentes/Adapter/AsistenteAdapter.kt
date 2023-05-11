package com.example.fedem.Asistentes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Asistentes.AsistentesActivity
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.R
import com.example.fedem.databinding.ActivityItemAsistentesBinding
import com.example.fedem.databinding.ActivityItemEventoBinding

class AsistenteAdapter(private var asistentes: MutableList<AsistenteEntity>, private var listener: AsistentesActivity):RecyclerView.Adapter<AsistenteAdapter.ViewHolder>(){
    private lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_asistentes, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asistentes = asistentes.get(position)

        with(holder) {
            setListener(asistentes)
            binding.TvNombre.text=asistentes.nombre
            binding.TvCorreo.text=asistentes.correo
            binding.TvBiografia.text=asistentes.biografia


            Glide.with(mContext)
                .load(asistentes.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.IvFotoPerfil)
        }
    }

    override fun getItemCount(): Int = asistentes.size

    fun setAsistente(asistentes: MutableList<AsistenteEntity>) {

        this.asistentes = asistentes
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val binding = ActivityItemAsistentesBinding.bind(view)

        fun setListener(asistenteEntity: AsistenteEntity) {
            binding.root.setOnClickListener {
                listener.onClick(asistenteEntity)
            }
        }
    }
}
