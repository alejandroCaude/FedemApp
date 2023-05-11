package com.example.fedem.Eventos.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.R
import com.example.fedem.databinding.ActivityItemEventoBinding

class EventoAdapter(private var eventos: MutableList<EventoEntity>, private var listener:OnClickListener):RecyclerView.Adapter<EventoAdapter.ViewHolder>(){
    private lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_evento, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento = eventos.get(position)

        with(holder) {
            setListener(evento)

            binding.tvDescripcion.text=evento.descripcion
            binding.tvNombre
                .text=evento.nombre

            Glide.with(mContext)
                .load(evento.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.IvEvento)
        }
    }

    override fun getItemCount(): Int = eventos.size

    fun setEventos(eventos: MutableList<EventoEntity>) {
        this.eventos = eventos
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val binding = ActivityItemEventoBinding.bind(view)

        fun setListener(eventoEntity: EventoEntity) {
            binding.root.setOnClickListener {
                listener.onClick(eventoEntity)
            }
        }
    }
}
