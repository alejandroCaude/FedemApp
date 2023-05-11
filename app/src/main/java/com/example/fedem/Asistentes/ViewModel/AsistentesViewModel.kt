package com.example.fedem.Asistentes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Actividades.Model.ActividadesInteractor
import com.example.fedem.Asistentes.Model.AsistentesInteractor
import com.example.fedem.Common.Entities.AsistenteEntity

class AsistentesViewModel: ViewModel() {
    private  var intetactor: AsistentesInteractor= AsistentesInteractor()

    private val asistentes: MutableLiveData<MutableList<AsistenteEntity>> by lazy {
        MutableLiveData<MutableList<AsistenteEntity>>().also {
            loadAsistentes()
        }
    }

    fun getAsistentes(): LiveData<MutableList<AsistenteEntity>> {
        return asistentes
    }

    private fun loadAsistentes() {
        intetactor.getAsistentes {
            asistentes.value = it
        }
    }
}