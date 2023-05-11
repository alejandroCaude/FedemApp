package com.example.fedem.Actividades

import android.app.Dialog
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Actividades.Model.ActividadesInteractor
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.databinding.FragmentInfoCompletaActividadesPopupBinding
import kotlinx.coroutines.*
import java.util.*

class InfoCompletaActividadesPopUpFragment : DialogFragment() {
    private var actId: Long = 0
    private lateinit var binding: FragmentInfoCompletaActividadesPopupBinding
    private var seguido=false
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actId = arguments?.getLong("id") ?: 0
        textToSpeech = TextToSpeech(requireActivity(), TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
            }
        })


    }

    override fun onPause() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onPause()
    }
    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        binding = FragmentInfoCompletaActividadesPopupBinding.inflate(inflater, container, false)


        GlobalScope.launch {
            AjenoActividad { result ->
                if (result) {
                    binding.btnDelete.visibility=View.VISIBLE
                } else {
                   binding.btnAnyadir.visibility=View.VISIBLE
                }
            }
        }


        binding.btnAnyadir.setOnClickListener{
            GlobalScope.launch {
                PostActividad(FedemApplication.asistente.id,actId)

            }

        }
        binding.btnDelete.setOnClickListener{
            GlobalScope.launch {
                deleteActividad(FedemApplication.asistente.id,actId)
            }
        }
        binding.closePopup.setOnClickListener{
            dismiss()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle("Información completa de la activiad")
        val actividadesInteractor = ActividadesInteractor()
        actividadesInteractor.getActividad(actId) { actividad ->
            if (actividad != null) {
                binding.tvDescripcion.setText(actividad.descripcion)
                binding.tvNombre.setText(actividad.nombre)
                binding.tvHorario.setText(actividad.horaInicio + " - " + actividad.horaFinal)
                binding.closePopup.visibility=View.VISIBLE

                Glide.with(requireContext())
                    .load(actividad.imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.IvEvento)

                binding.FbEscuchar.setOnClickListener {
                    textToSpeech.speak(actividad.descripcion, TextToSpeech.QUEUE_FLUSH, null)
                }

            }
        }

        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    private fun PostActividad(idAjeno: Long, idActividad: Long) {
        //val url = "http://apicei58.ieslasenia.org/ajeno/$idAjeno/actividad/$idActividad"
        val url = "${Constants.API_URL}${Constants.POST_AJENO_ACTIVIDAD_PATH.replace("{id_ajeno}", idAjeno.toString()).replace("{id_actividad}", idActividad.toString())}"
        try {
            val request = JsonObjectRequest(
                com.android.volley.Request.Method.POST,
                url,
                null,
                { response ->
                    // Aquí se procesa la respuesta, que es un array JSON
                    Toast.makeText(requireActivity(), "Actividad agregada", Toast.LENGTH_SHORT).show()

                },
                { error ->
                    // Aquí se procesa el error
                    Toast.makeText(requireActivity(), "no se agrega", Toast.LENGTH_SHORT).show()
                }
            )

            // Agrega la petición a la cola de peticiones de Volley
            Volley.newRequestQueue(requireActivity()).add(request)
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), "Ocurrió un error al agregar", Toast.LENGTH_SHORT).show()
            e.message?.let {
                Log.e("addActividad", it)
            }
        }
    }

    private fun deleteActividad(idAjeno: Long, idActividad: Long) {
        val url = "${Constants.API_URL}${Constants.DELETE_AJENO_ACTIVIDAD_PATH.replace("{id_ajeno}", idAjeno.toString()).replace("{id_actividad}", idActividad.toString())}"
        try {
            val request = JsonObjectRequest(
                com.android.volley.Request.Method.DELETE,
                url,
                null,
                { response ->
                    //respiesta
                    Toast.makeText(requireActivity(), " Actividad eliminada", Toast.LENGTH_SHORT).show()

                },
                { error ->
                    //error
                    Log.e("deleteActividad", error.toString())
                    Toast.makeText(requireActivity(), "no se elimina", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(requireActivity()).add(request)
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), "Ocurrió un error al eliminar", Toast.LENGTH_SHORT).show()
            e.message?.let {
                Log.e("deleteActividad", it)
            }
        }
    }

    private suspend fun AjenoActividad(callback: (Boolean) -> Unit) {
        val actividadesAjenoInteractor = ActividadesInteractor()
        actividadesAjenoInteractor.getActividadesAjeno(FedemApplication.asistente.id) { actividades ->
            if (actividades.any { it.id == actId }) {
                seguido = true
                Log.i("hola", "encontrado")
                callback(true)
            } else {
                Log.i("hola", "no encontrado")
                seguido = false
                callback(false)
            }
        }
    }



}