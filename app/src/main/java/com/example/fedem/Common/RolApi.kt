package com.example.fedem.Common

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.Common.Entities.SocioEntity
import com.example.fedem.Common.Utils.Constants
import com.google.gson.Gson

class RolApi() {
    fun getSocio(id: Long,callback: (SocioEntity?) -> Unit) {
        val url = "${Constants.API_URL}${Constants.GET_SOCIO_PATH}".replace("{id}",id.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val socio = Gson().fromJson(response.toString(), SocioEntity::class.java)
                FedemApplication.rol="socio"
                callback(socio)
            }, {
                it.printStackTrace()
                FedemApplication.rol="ajeno"
                callback(null)
            })

        FedemApplication.eventoApi.addToRequestQueue(jsonObjectRequest)
    }
}