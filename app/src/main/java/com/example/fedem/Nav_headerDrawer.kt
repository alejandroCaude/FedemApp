package com.example.fedem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fedem.Common.FedemApplication
import com.example.fedem.databinding.ActivityNavHeaderDrawerBinding

class Nav_headerDrawer : AppCompatActivity() {
    private lateinit var binding : ActivityNavHeaderDrawerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHeaderDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("HOLAAA","HOLA")
//        binding.TvnombreUsuario.text=FedemApplication.asistente.nombreUsuario
//        binding.TvCorreo.text=FedemApplication.asistente.correo
    }
}