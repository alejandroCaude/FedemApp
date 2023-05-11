package com.example.fedem.Comidas

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fedem.Actividades.Model.ActividadesInteractor
import com.example.fedem.Comidas.Model.ComidaInteractor
import com.example.fedem.Common.FedemApplication
import com.example.fedem.MenuPrincipal.MenuActivity
import com.example.fedem.R
import com.example.fedem.databinding.FragmentInfoCompletaComidasPopupBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class InfoCompletaComidasPopUpFragment : DialogFragment() {
    private var comidaId: Long = 0
    private lateinit var binding: FragmentInfoCompletaComidasPopupBinding
    private var bono=false
    private var puesto="puesto"
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comidaId = arguments?.getLong("id") ?: 0
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
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        binding = FragmentInfoCompletaComidasPopupBinding.inflate(inflater, container, false)
        binding.closePopup.setOnClickListener{
            dismiss()
        }
        GlobalScope.launch {
            BonosComidas { result ->
                if (result) {
                    binding.btnDelete.visibility=View.VISIBLE
                } else {
                    binding.btnDelete.visibility=View.GONE
                    binding.TvBonoUsado.visibility=View.VISIBLE
                }
            }
        }
        binding.btnDelete.setOnClickListener{
            GlobalScope.launch {
                deleteBono(FedemApplication.asistente.id,comidaId)
            }
        }
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle("Información completa de comida")
        val comidaInteractor = ComidaInteractor()
        comidaInteractor.getComida(comidaId) { comidas ->
            if (comidas != null) {
                binding.tvDescripcion.setText(comidas.descripcion)
                binding.tvNombre.setText(comidas.nombre)
                binding.tvZona.setText(comidas.lugar)
                puesto=comidas.nombre
                binding.tvTipoComida.setText(comidas.tipoComida)
                binding.closePopup.visibility=View.VISIBLE

                Glide.with(requireContext())
                    .load(comidas.imagen)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.IvEvento)

                binding.FbEscuchar.setOnClickListener {
                    textToSpeech.speak(comidas.descripcion, TextToSpeech.QUEUE_FLUSH, null)
                }


            }
        }
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteBono(idAsistente: Long, idComida: Long) {
        val url = "http://apicei58.ieslasenia.org/asistente/$idAsistente/bono/$idComida"

        try {
            val request = JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                { response ->
                    //respiesta
                    context?.let { it1 ->
                        MaterialAlertDialogBuilder(it1)
                            .setTitle(R.string.BonoComidaAceptar)
                            .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                                Toast.makeText(requireActivity(), " Bono usado", Toast.LENGTH_SHORT).show()
                                notificacion()
                            }
                            .setNegativeButton(R.string.dialog_delete_cancel,null)
                            .show()
                    }


                },
                { error ->
                    //error
                    Log.e("deleteBono", error.toString())
                    Toast.makeText(requireActivity(), "no se elimina", Toast.LENGTH_SHORT).show()
                }
            )
            Volley.newRequestQueue(requireActivity()).add(request)
        } catch (e: Exception) {
            Toast.makeText(requireActivity(), "Ocurrió un error al eliminar", Toast.LENGTH_SHORT).show()
            e.message?.let {
                Log.e("deleteBono", it)
            }
        }
    }
    private suspend fun BonosComidas(callback: (Boolean) -> Unit) {
        val comidaInteractor = ComidaInteractor()
        comidaInteractor.getBonosComida(FedemApplication.asistente.id){ bonos ->
            if (bonos.any { it.id == comidaId }) {
                bono = true
                callback(true)
            } else {
                bono = false
                callback(false)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun notificacion() {
        val channelId = "channel_id"
        val channelName = "channel_name"
        val importance = NotificationManager.IMPORTANCE_HIGH

// Crear canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(requireContext(), ComidasFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Que aproveche "+FedemApplication.asistente.nombre+"!!")
            .setContentText("Has usado el bono para "+puesto)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(1, builder.build())

    }

    fun notificacionProgramada() {
        val channelId = "channel_id"
        val channelName = "channel_name"
        val importance = NotificationManager.IMPORTANCE_HIGH

// Crear canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Crear intent para abrir la actividad ComidasFragment
        val intent = Intent(requireContext(), ComidasFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

// Crear notificación
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Que aproveche "+FedemApplication.asistente.nombre+"!!")
            .setContentText("Has usado el bono para "+puesto)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

// Programar la alarma para que se ejecute todos los días a las 14:00 de la tarde
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 16)
        calendar.set(Calendar.SECOND, 0)
        val alarmTime = calendar.timeInMillis

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

// Mostrar un mensaje para confirmar que la notificación se ha programado correctamente
        Toast.makeText(requireContext(), "Notificación programada para las 14:00", Toast.LENGTH_SHORT).show()

    }


}