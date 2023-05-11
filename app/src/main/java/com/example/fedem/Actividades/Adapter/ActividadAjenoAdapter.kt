package com.example.fedem.Actividades.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Actividades.Adapter.OnClickListenerActividades
import com.example.fedem.R
import com.example.fedem.databinding.ActivityItemEventoBinding

class ActividadAjenoAdapter(private var actividades: MutableList<ActividadEntity>, private var listener: OnClickListenerActividades):RecyclerView.Adapter<ActividadAjenoAdapter.ViewHolder>(){
    private lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context

        val view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_evento, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("ERROR","SE METE")
        val actividad = actividades.get(position)

        with(holder) {
            setListener(actividad)

            binding.tvDescripcion.text=actividad.descripcion
            binding.tvNombre.text=actividad.nombre

            Glide.with(mContext)
                .load(actividad.imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.IvEvento)
        }
    }

    override fun getItemCount(): Int = actividades.size

    fun setActividades(actividades: MutableList<ActividadEntity>) {
        this.actividades = actividades
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val binding = ActivityItemEventoBinding.bind(view)

        fun setListener(actividadEntity: ActividadEntity) {
            binding.root.setOnClickListener {
                listener.onClick(actividadEntity)
            }
        }
    }
}
