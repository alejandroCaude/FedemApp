package com.example.fedem.Login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Common.Utils.Constants
import com.example.fedem.MenuPrincipal.MenuActivity
import com.example.fedem.R
import com.example.fedem.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences("Detalles", MODE_PRIVATE)

        guardar()
        binding.TvRegistrate.setOnClickListener{
            val WebUrl = "https://www.fedem.online"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(WebUrl))

            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun login() {
        val intent = Intent(this, MenuActivity::class.java)
        val email = binding.TietCorreo.text.toString().trim()
        val password =  binding.TietPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)
        if(email.isEmpty()||password.isEmpty()){
            binding.Tverror.visibility=View.VISIBLE
            binding.Tverror.text="Rellena todos los campos"
        }

        // Con Corrutinas
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val result = service.loginUser(email, password)
                withContext(Dispatchers.Main) {
                    //usuario en memoria para la app
                    FedemApplication.asistente = result
                    remember()
                    // transicion a donde sea
                    startActivity(intent)
                    notificacion()
                    finish()
                }
                binding.Tverror.visibility = View.VISIBLE
                binding.Tverror.text="Usuario o contraseña Incorrectos"
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                            Log.i("ERROR4", "NO ENTRA")
                        }
                        else ->
                            updateUI(getString(R.string.main_error_response))


                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notificacion(){
        // Crear notificación
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "channel_id"
        val channelName = "channel_name"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(notificationChannel)

        // Lanzar notificación
        val notificationIntent = Intent(this, MenuActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Login exitoso")
            .setContentText(FedemApplication.asistente.nombre + " has iniciado sesión exitosamente")
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }


    private fun guardar() {
        if (preferences.getBoolean("remember", false)) {
            var asistenteJson = preferences.getString("cliente", "")
            if (!asistenteJson!!.isEmpty()) {
                var asistenteEntity = Gson().fromJson(asistenteJson, AsistenteEntity::class.java)
                FedemApplication.asistente = asistenteEntity

                startActivity(Intent(this, MenuActivity::class.java))
            }
        }
    }

    private fun remember() {
        val preference: SharedPreferences.Editor = preferences.edit()
        if (binding.checkbox.isChecked) {
            preference.putBoolean("remember", true).apply()
            if (!FedemApplication.asistente.nombre.isEmpty()) {
                var asistenteJson = Gson().toJson(FedemApplication.asistente)
                val preference: SharedPreferences.Editor = preferences.edit()
                preference.putString("cliente", asistenteJson).apply()
            }
        } else {
            preference.putBoolean("remember", false).apply()
        }
    }


    suspend fun updateUI(result: String) = withContext(Dispatchers.Main) {
        binding.Tverror.visibility = View.VISIBLE
        binding.Tverror.text = result
    }


}