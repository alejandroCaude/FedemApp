package com.example.fedem.Eventos.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Common.Entities.EventoEntity
import com.example.fedem.Eventos.Model.EventosInteractor

class EventosViewModel: ViewModel() {
    private  var intetactor: EventosInteractor = EventosInteractor()

    private val eventos: MutableLiveData<MutableList<EventoEntity>> by lazy {
        MutableLiveData<MutableList<EventoEntity>>().also {
            loadEventos()
        }
    }

    fun getEventos(): LiveData<MutableList<EventoEntity>> {
        return eventos
    }

    private fun loadEventos() {
        intetactor.getEventos {
            eventos.value = it
        }
    }
}