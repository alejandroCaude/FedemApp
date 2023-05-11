package com.example.fedem.Actividades.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Actividades.Model.ActividadesInteractor

class ActividadesViewModel: ViewModel() {
    private  var intetactor: ActividadesInteractor = ActividadesInteractor()

    private val actividades: MutableLiveData<MutableList<ActividadEntity>> by lazy {
        MutableLiveData<MutableList<ActividadEntity>>().also {
            loadActividades()
        }
    }

    fun getActividades(): LiveData<MutableList<ActividadEntity>> {
        return actividades
    }

    private fun loadActividades() {
        intetactor.getActividades {
            actividades.value = it
        }

    }
}