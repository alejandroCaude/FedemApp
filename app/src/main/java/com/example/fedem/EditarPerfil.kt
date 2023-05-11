package com.example.fedem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.MenuPrincipal.MenuActivity
import com.example.fedem.Perfil.PerfilActivity
import com.example.fedem.databinding.ActivityEditarPerfilBinding
import org.json.JSONObject

class EditarPerfil : AppCompatActivity() {
    private lateinit var binding: ActivityEditarPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var NombreCompleto= FedemApplication.asistente.nombre + " " + FedemApplication.asistente.apellido
        binding.etNombre.text= Editable.Factory.getInstance().newEditable(NombreCompleto)
        binding.TvNombreUsuario.text= Editable.Factory.getInstance().newEditable(FedemApplication.asistente.nombreUsuario)
        binding.TvCorreo.text= Editable.Factory.getInstance().newEditable(FedemApplication.asistente.correo)
        binding.TvDni.text= Editable.Factory.getInstance().newEditable(FedemApplication.asistente.dni.toString())
        binding.TvTelefono.text= Editable.Factory.getInstance().newEditable(FedemApplication.asistente.telefono.toString())
        binding.TvNumTelefonoContacto.text= Editable.Factory.getInstance().newEditable(FedemApplication.asistente.telefonoContacto.toString())

        binding.etNombre.clearFocus()
        binding.btnSaveProfile.requestFocus()

        Glide.with(this)
            .load(FedemApplication.asistente.imagen)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.IvPerfil)

        binding.btnSaveProfile.setOnClickListener {
            actualizarDatos(FedemApplication.asistente.id)
        }
    }


    private fun actualizarDatos(id: Long) {
        var nombreCompleto= binding.etNombre.text.toString().split(" ")
        var nombre = nombreCompleto[0]
        var apellido = nombreCompleto[1]
        var usuario= binding.TvNombreUsuario.text.toString()
        var correo= binding.TvCorreo.text.toString()
        var dni= binding.TvDni.text.toString()
        var numTelefono= binding.TvTelefono.text.toString().toInt()
        var telefonoContacto= binding.TvNumTelefonoContacto.text.toString().toInt()
        val url = "${Constants.API_URL}/asistente/$id"
        val parametros = JSONObject().apply {
            put("nombre", nombre)
            put("contrasenya", apellido)
            put("nombreUsuario", usuario)
            put("correo", correo)
            put("dni", dni)
            put("telefono", numTelefono)
            put("telefonoContacto", telefonoContacto)
        }

        val request = object : JsonObjectRequest(
            Method.PUT,
            url,
            parametros,
            { response ->
                Log.i("HOLA",response.toString())
                Toast.makeText(this, "Cambios realizados correctamente", Toast.LENGTH_SHORT).show()
                // Actualiza el objeto asistente con los nuevos datos
                FedemApplication.asistente.nombre = nombre
                FedemApplication.asistente.apellido = apellido
                FedemApplication.asistente.nombreUsuario = usuario
                FedemApplication.asistente.correo = correo
                FedemApplication.asistente.dni = dni
                FedemApplication.asistente.telefono = numTelefono
                FedemApplication.asistente.telefonoContacto = telefonoContacto

                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
            },
            { error ->
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)
    }

}