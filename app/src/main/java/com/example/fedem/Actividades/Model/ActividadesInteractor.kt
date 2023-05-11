package com.example.fedem.Actividades.Model

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Login.LoginService
import com.example.fedem.MenuPrincipal.MenuActivity
import com.example.fedem.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ActividadesInteractor() {

    fun getActividades(callback: (MutableList<ActividadEntity>) -> Unit) {
        val url = Constants.API_URL + Constants.GET_ALL_ACTIVIDADES_PATH

        var actividadesList = mutableListOf<ActividadEntity>()
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val mutableListType = object : TypeToken<MutableList<ActividadEntity>>() {}.type
                actividadesList =
                    Gson().fromJson(response.toString(), mutableListType)

                callback(actividadesList)
                return@JsonArrayRequest

            }, {
                it.printStackTrace()
                callback(actividadesList)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonArrayRequest)
    }

    fun getActividad(id: Long,callback: (ActividadEntity?) -> Unit) {
        val url = "${Constants.API_URL}${Constants.GET_ACTIVIDAD_PATH}".replace("{id}",id.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, { response ->
                Log.i("Response", response.toString())
                val actividad = Gson().fromJson(response.toString(), ActividadEntity::class.java)
                callback(actividad)
            }, {
                it.printStackTrace()
                callback(null)
            })
        FedemApplication.eventoApi.addToRequestQueue(jsonObjectRequest)
    }
    suspend fun getActividadesAjeno(id: Long, param: (List<ActividadEntity>) -> Unit) {
        return withContext(Dispatchers.IO) {
            val url = "${Constants.API_URL}${Constants.GET_ACTIVIDADES_AJENO_PATH}".replace("{id_ajeno}",id.toString())
            val response = URL(url).readText()
            Log.i("Funciona", response)
            val actividades = Gson().fromJson(response, Array<ActividadEntity>::class.java)
            withContext(Dispatchers.Main) {
                param(actividades.toList())
            }
        }
    }


}