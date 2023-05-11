package com.example.fedem.Comidas.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Comidas.Model.ComidaInteractor
import com.example.fedem.Common.Entities.ComidaEntity

class InfoComidaViewModel: ViewModel() {
    private  var interactor: ComidaInteractor = ComidaInteractor()
    private val _comida = MutableLiveData<ComidaEntity>()

    val comida: LiveData<ComidaEntity>
        get() = _comida

    fun loadComida(id: Long) {
        interactor.getComida(id) { comida ->
            _comida.value = comida
        }
    }

}