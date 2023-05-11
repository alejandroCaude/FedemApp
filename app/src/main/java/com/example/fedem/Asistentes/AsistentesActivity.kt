package com.example.fedem.Asistentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fedem.Asistentes.Adapter.AsistenteAdapter
import com.example.fedem.Asistentes.ViewModel.AsistentesViewModel
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.R
import com.example.fedem.databinding.ActivityAsistentesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AsistentesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsistentesBinding
    private lateinit var mAsistentesViewModel: AsistentesViewModel
    private lateinit var mAsistentesAdapter: AsistenteAdapter
    private lateinit var mGridLayout: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAsistentesViewModel = ViewModelProvider(this).get(AsistentesViewModel::class.java)
        binding = ActivityAsistentesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        mAsistentesViewModel = ViewModelProvider(this).get(AsistentesViewModel::class.java)
        mAsistentesViewModel.getAsistentes().observe(this) { asistente ->
            mAsistentesAdapter.setAsistente(asistente)
        }
    }

    private fun setupRecyclerView() {
        mAsistentesAdapter = AsistenteAdapter(mutableListOf(), this)
        mGridLayout = LinearLayoutManager(this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAsistentesAdapter
        }
    }

    fun onClick(asistenteEntity: AsistenteEntity) {
        val asuntoCorreo=asistenteEntity.nombre + " quiere enviarte un mensaje, desde Fedem App"
        this?.let { it1 ->
            MaterialAlertDialogBuilder(it1)
                .setTitle(R.string.ContactarAsistente)
                .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                    contacto(asuntoCorreo,asistenteEntity.correo)
                }
                .setNegativeButton(R.string.dialog_delete_cancel,null)
                .show()
        }
    }

    fun contacto(Asunto:String,Correo:String){

            val asunto=Asunto
            val address =Correo

            val intent = Intent(Intent.ACTION_SEND).apply {
                type ="text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                putExtra(Intent.EXTRA_SUBJECT, asunto)

            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent,"Enviar correo"))
            }

    }
}
