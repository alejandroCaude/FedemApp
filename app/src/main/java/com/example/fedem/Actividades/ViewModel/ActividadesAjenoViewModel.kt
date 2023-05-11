package com.example.fedem.Actividades.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Actividades.Model.ActividadesInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActividadesAjenoViewModel: ViewModel() {
    private  var intetactor: ActividadesInteractor = ActividadesInteractor()

    private val actividades: MutableLiveData<MutableList<ActividadEntity>> by lazy {
        MutableLiveData<MutableList<ActividadEntity>>().also {
            Log.i("ERROR4","SE METE")
            loadActividades(1)
        }
    }

    fun getActividades(): LiveData<MutableList<ActividadEntity>> {
        Log.i("ERROR3","SE METE")
        return actividades
    }

    private fun loadActividades(id: Long) {
        Log.i("ERROR5","SE METE")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                Log.i("ERROR7","se mete")
                intetactor.getActividadesAjeno(id) {
                    Log.i("ERROR8"," se mete")
                    if (it != null) {
                        Log.i("ERROR6", it.toString())
                        actividades.value = it as MutableList<ActividadEntity>?
                    }
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("ERROR11", it) }
            }
        }
    }


}