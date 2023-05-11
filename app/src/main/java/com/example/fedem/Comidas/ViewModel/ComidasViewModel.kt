package com.example.fedem.Comidas.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Comidas.Model.ComidaInteractor
import com.example.fedem.Common.Entities.ComidaEntity


class ComidasViewModel: ViewModel() {
    private  var intetactor: ComidaInteractor = ComidaInteractor()

    private val comidas: MutableLiveData<MutableList<ComidaEntity>> by lazy {
        MutableLiveData<MutableList<ComidaEntity>>().also {
            loadComidas()
        }
    }

    fun getComidas(): LiveData<MutableList<ComidaEntity>> {
        return comidas
    }

    private fun loadComidas() {
        intetactor.getComidas {
            comidas.value = it
        }
    }
}