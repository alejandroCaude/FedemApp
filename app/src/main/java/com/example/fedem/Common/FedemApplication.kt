package com.example.fedem.Common

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fedem.Common.DataBase.EventoApi
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FedemApplication: Application() {

    companion object {
        lateinit var eventoApi: EventoApi
        lateinit var asistente: AsistenteEntity
        var rol=""
    }


    override fun onCreate(){
        super.onCreate()
        //volley
        eventoApi = EventoApi(this)


    }


}