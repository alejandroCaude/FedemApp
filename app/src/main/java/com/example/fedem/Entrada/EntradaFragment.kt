package com.example.fedem.Entradas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Entrada.Model.EntradaIteractor
import com.example.fedem.databinding.FragmentEntradaBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class EntradaFragment : Fragment() {

    private var entradaId: Long = 0
    private lateinit var binding : FragmentEntradaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        entradaId = arguments?.getLong("id")?:0


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentEntradaBinding.inflate(inflater, container, false)

        val numEntrda = "12345"
        crearQR(numEntrda)

        var entradaIteractor = EntradaIteractor()
        entradaIteractor.getEntrada(FedemApplication.asistente.id){ entrada ->
            if(entrada != null){
                binding.entrNombre.text = entrada.asistente.nombre
                binding.entrApellidos.text = entrada.asistente.apellido
                binding.entrCorreo.text = entrada.asistente.correo
                binding.TvDni.text = entrada.asistente.dni.toString()

                crearQR(entrada.id.toString())
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    //    Crea un qr segun una string
    fun crearQR(numEntrada: String){
        val imagenQR = binding.imagenQR
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(numEntrada, BarcodeFormat.QR_CODE, 750, 750)

            imagenQR.setImageBitmap(bitmap)

        } catch (e: Exception) {
//            poner imagen de no carga
            Log.e("Imagen", "Imagen no carga")
        }
    }

}