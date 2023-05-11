package com.example.fedem.Asistentes.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class AsistentesInteractor() {

    fun getAsistentes(callback: (MutableList<AsistenteEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_ASISTENTES_PATH
        Log.i("url_asistentes",url)

        var asistentesList = mutableListOf<AsistenteEntity>()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val mutableListType = object : TypeToken<MutableList<AsistenteEntity>>() {}.type
                asistentesList =
                    Gson().fromJson(response.toString(), mutableListType)

                callback(asistentesList)
                return@JsonArrayRequest

            }, {
                Log.i("responseError",it.toString())
                it.printStackTrace()
                callback(asistentesList)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonArrayRequest)
    }
}