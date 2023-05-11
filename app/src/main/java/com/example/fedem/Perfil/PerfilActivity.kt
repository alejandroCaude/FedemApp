package com.example.fedem.Perfil

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Common.FedemApplication
import com.example.fedem.EditarPerfil
import com.example.fedem.R
import com.example.fedem.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var NombreCompleto= FedemApplication.asistente.nombre + " " + FedemApplication.asistente.apellido
        binding.TvNombre.text=NombreCompleto
        binding.TvNombreUsuario.text=FedemApplication.asistente.nombreUsuario
        binding.TvCorreo.text=FedemApplication.asistente.correo
        binding.TvProvincia.text= FedemApplication.asistente.provincia
        binding.TvGenero.text= FedemApplication.asistente.genero
        binding.TvDni.text= FedemApplication.asistente.dni.toString()
        binding.TvNumTelefono.text= FedemApplication.asistente.telefono.toString()
        binding.TvNumTelefonoContacto.text= FedemApplication.asistente.telefonoContacto.toString()

        Glide.with(this)
            .load(FedemApplication.asistente.imagen)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.IvPerfil)


        binding.btnPutProfile.setOnClickListener{
            editar()

        }

    }


    fun editar(){
        val intent = Intent(this, EditarPerfil::class.java)
        startActivity(intent)

    }
}