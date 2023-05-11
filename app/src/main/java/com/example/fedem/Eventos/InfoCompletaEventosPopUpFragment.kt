package com.example.fedem.Eventos

import android.app.Dialog
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Eventos.Model.EventosInteractor
import com.example.fedem.databinding.FragmentInfoCompletaEventosPopupBinding
import java.util.*

class InfoCompletaEventosPopUpFragment : DialogFragment() {
    private var eventId: Long = 0
    private lateinit var binding: FragmentInfoCompletaEventosPopupBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = arguments?.getLong("id") ?: 0
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        binding = FragmentInfoCompletaEventosPopupBinding.inflate(inflater, container, false)
        binding.closePopup.setOnClickListener{
            dismiss()
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle("Información completa del evento")
        val eventosInteractor = EventosInteractor()
        eventosInteractor.getEvento(eventId) { eventos ->
            if (eventos != null) {
                binding.tvDescripcion.text = eventos.descripcion
                binding.tvNombre.text = eventos.nombre
                binding.tvHorario.text = eventos.horaInicio + " - " + eventos.horaFin
                binding.tvZona.text = eventos.lugar
                binding.tvNumDia.text = eventos.dia.toString()
                binding.tvDia.visibility=View.VISIBLE
                binding.closePopup.visibility=View.VISIBLE

                Glide.with(requireContext())
                    .load(eventos.imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.IvEvento)

                binding.FbEscuchar.setOnClickListener {
                    textToSpeech.speak(eventos.descripcion, TextToSpeech.QUEUE_FLUSH, null)
                }

            }
        }

        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }
}