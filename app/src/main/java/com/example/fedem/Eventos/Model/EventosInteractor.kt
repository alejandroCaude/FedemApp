package com.example.fedem.Eventos.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EventosInteractor() {

    fun getEventos(callback: (MutableList<EventoEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_EVENTOS_PATH

        var eventsList = mutableListOf<EventoEntity>()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val mutableListType = object : TypeToken<MutableList<EventoEntity>>() {}.type
                eventsList =
                    Gson().fromJson(response.toString(), mutableListType)

                callback(eventsList)
                return@JsonArrayRequest

            }, {
                it.printStackTrace()
                callback(eventsList)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonArrayRequest)
    }

    fun getEvento(id: Long,callback: (EventoEntity?) -> Unit) {
        val url = "${Constants.API_URL}${Constants.GET_EVENTO_PATH}".replace("{id}",id.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val evento = Gson().fromJson(response.toString(), EventoEntity::class.java)
                callback(evento)
            }, {
                it.printStackTrace()
                callback(null)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonObjectRequest)
    }
}