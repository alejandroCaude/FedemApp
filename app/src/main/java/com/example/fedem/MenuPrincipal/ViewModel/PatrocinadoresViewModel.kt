package com.example.fedem.MenuPrincipal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedem.Common.Entities.PatrocinadoresEntity
import com.example.fedem.MenuPrincipal.Model.PatrocinadoresInteractor

class PatrocinadoresViewModel: ViewModel() {
    private  var intetactor: PatrocinadoresInteractor = PatrocinadoresInteractor()
    private var patrocinadoresList:MutableList<PatrocinadoresEntity> = mutableListOf()

    private val patrocinadores: MutableLiveData<MutableList<PatrocinadoresEntity>> by lazy {
        MutableLiveData<MutableList<PatrocinadoresEntity>>().also {
            loadPatrocinadores()
        }
    }

    fun getPatrocinadores(): LiveData<MutableList<PatrocinadoresEntity>> {
        return patrocinadores
    }

    private fun loadPatrocinadores() {
        intetactor.getPatrocinadores {
            patrocinadores.value = it
            patrocinadoresList= it
        }
    }
}