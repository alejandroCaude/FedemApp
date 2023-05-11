package com.example.fedem.Comidas.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Common.Entities.ComidaEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ComidaInteractor {
    fun getComidas(callback: (MutableList<ComidaEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_COMIDAS_PATH

        var comidasList = mutableListOf<ComidaEntity>()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val mutableListType = object : TypeToken<MutableList<ComidaEntity>>() {}.type
                comidasList =
                    Gson().fromJson(response.toString(), mutableListType)

                callback(comidasList)
                return@JsonArrayRequest

            }, {
                it.printStackTrace()
                callback(comidasList)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonArrayRequest)
    }

    fun getComida(id: Long,callback: (ComidaEntity?) -> Unit) {
        val url = "${Constants.API_URL}${Constants.GET_COMIDA_PATH}".replace("{id}",id.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val comida = Gson().fromJson(response.toString(), ComidaEntity::class.java)
                callback(comida)
            }, {
                it.printStackTrace()
                callback(null)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonObjectRequest)
    }
    suspend fun getBonosComida(id: Long, param: (List<ComidaEntity>) -> Unit) {
        return withContext(Dispatchers.IO) {
            val url = "${Constants.API_URL}${Constants.GET_BONOS_ASISTENTES_PATH}".replace("{id_asistente}",id.toString())
            val response = URL(url).readText()
            Log.i("Funciona", response)
            val bonos = Gson().fromJson(response, Array<ComidaEntity>::class.java)
            withContext(Dispatchers.Main) {
                param(bonos.toList())
            }
        }
    }
}