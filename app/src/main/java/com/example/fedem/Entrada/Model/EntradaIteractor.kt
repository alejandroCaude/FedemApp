package com.example.fedem.Entrada.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.EntradaEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.google.gson.Gson

class EntradaIteractor {

    fun getEntrada(id: Long,callback: (EntradaEntity?) -> Unit) {
        val url = "${Constants.API_URL}${Constants.GET_ENTRADA_PATH}".replace("{id}", id.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val entrada = Gson().fromJson(response.toString(), EntradaEntity::class.java)
                callback(entrada)
            }, {
                it.printStackTrace()
                callback(null)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonObjectRequest)
    }

}