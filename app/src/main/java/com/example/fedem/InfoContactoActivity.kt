package com.example.fedem

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fedem.databinding.ActivityInfoContactoBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.String
import java.util.*


class InfoContactoActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityInfoContactoBinding
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityInfoContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createFragment()
        cargarBotones()


    }

    private fun createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        configurarMarcador()
    }

    private fun configurarMarcador(){
        val madrid = LatLng(40.45663300191464, -3.661347518814818)
        val marcador: MarkerOptions = MarkerOptions().position(madrid).title("Cogreso Fedem")

        mMap.addMarker(marcador)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(madrid, 10f), 2000, null)

    }

    private fun cargarBotones(){

        binding.btnCall.setOnClickListener{
            val telefono = "91 744 47 00"
            val u = Uri.parse("tel:" + telefono)

            val i = Intent(Intent.ACTION_DIAL, u)
            try {
                startActivity(i)
            }catch (s:SecurityException){
                Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnMail.setOnClickListener{
            val asunto="Incidencia"
            val address = "fedem@cetm.es"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type ="text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                putExtra(Intent.EXTRA_SUBJECT, asunto)

            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent,"Enviar correo"))
            }
        }

        binding.btnTwitter.setOnClickListener {
            // Create intent using ACTION_VIEW and a normal Twitter url:
            val tweetUrl = "https://twitter.com/Fedemudanzas?ref_src=twsrc%5Etfw%7Ctwcamp%5Eembeddedtimeline%7Ctwterm%5Escreen-name%3AFedemudanzas%7Ctwcon%5Es1_c1"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl))

            startActivity(intent)

        }

        binding.btnFacebook.setOnClickListener {
            val fbUrl = "https://www.facebook.com/login/?next=https%3A%2F%2Fwww.facebook.com%2FFEDEMmudanzas%2F"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fbUrl))

            startActivity(intent)

        }












    }

}