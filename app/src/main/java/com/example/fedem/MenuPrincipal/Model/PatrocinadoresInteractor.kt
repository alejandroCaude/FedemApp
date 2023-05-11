package com.example.fedem.MenuPrincipal.Model

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.Common.Entities.PatrocinadoresEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PatrocinadoresInteractor() {

    fun getPatrocinadores(callback: (MutableList<PatrocinadoresEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_PATROCINADORES_PATH
        var PatrocinadoresList = mutableListOf<PatrocinadoresEntity>()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("PatrocinadoresList", response.toString())
                val mutableListType = object : TypeToken<MutableList<PatrocinadoresEntity>>() {}.type
                PatrocinadoresList =
                    Gson().fromJson(response.toString(), mutableListType)

                callback(PatrocinadoresList)
                return@JsonArrayRequest

            }, {
                it.printStackTrace()
                callback(PatrocinadoresList)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonArrayRequest)
    }
}