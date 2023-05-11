package com.example.fedem.MenuPrincipal

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.fedem.databinding.ActivityMenuBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.fedem.*
import com.example.fedem.Actividades.ActividadesFragment
import com.example.fedem.Asistentes.AsistentesActivity
import com.example.fedem.Comidas.ComidasFragment
import com.example.fedem.Common.Entities.PatrocinadoresEntity
import com.example.fedem.Common.FedemApplication
import com.example.fedem.Common.RolApi
import com.example.fedem.Entradas.EntradaFragment
import com.example.fedem.Eventos.EventosFragment
import com.example.fedem.Login.MainActivity
import com.example.fedem.MenuPrincipal.ViewModel.PatrocinadoresViewModel
import com.example.fedem.Perfil.PerfilActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener


class MenuActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMenuBinding
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mActiveFragment: Fragment
    private  var mPatrocinadorViewModel: PatrocinadoresViewModel=PatrocinadoresViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomMenuLauncher()
        DrawerMenu()
        setUpImageCarrousel()



        val rolApi = RolApi()
        rolApi.getSocio(FedemApplication.asistente.id) { response ->
            if (FedemApplication.rol == "socio") {
                binding.BottomAppBar.navegationMenu.menu.findItem(R.id.eventos)
                    .setTitle("Eventos")
            } else {
                binding.BottomAppBar.navegationMenu.menu.findItem(R.id.eventos)
                        .setTitle("Actividades")
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun bottomMenuLauncher(){
        mFragmentManager = supportFragmentManager
        val eventosFragment= EventosFragment()
        val actividadesFragment= ActividadesFragment()
        val entradaFragment= EntradaFragment()
        val comidasFragment= ComidasFragment()
        val informacionUtilFragment= InformacionUtilFragment()

        mActiveFragment = entradaFragment


        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, eventosFragment,
                EventosFragment::class.java.name)
            .hide(eventosFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, actividadesFragment,
                ActividadesFragment::class.java.name)
            .hide(actividadesFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, entradaFragment,
                EntradaFragment::class.java.name)
            .hide(entradaFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, comidasFragment,
                ComidasFragment::class.java.name)
            .hide(comidasFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, informacionUtilFragment,
                InformacionUtilFragment::class.java.name)
            .hide(informacionUtilFragment).commit()

        var navBar = binding.BottomAppBar.navegationMenu
        navBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.inicio -> {
                    var i = Intent(this, MenuActivity::class.java)
                    startActivity(i)
                    true
                }
                R.id.entrada -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment)
                        .show(entradaFragment).commit()
                    mActiveFragment = entradaFragment
                    true
                }
                R.id.eventos -> {
                    if (FedemApplication.rol=="socio"){
                        mFragmentManager.beginTransaction().hide(mActiveFragment)
                            .show(eventosFragment).commit()
                        mActiveFragment = eventosFragment
                    }else{
                        mFragmentManager.beginTransaction().hide(mActiveFragment)
                            .show(actividadesFragment).commit()
                        mActiveFragment = actividadesFragment
                    }

                    true
                }
              R.id.comidas -> {
                  mFragmentManager.beginTransaction().hide(mActiveFragment)
                      .show(comidasFragment).commit()
                  mActiveFragment = comidasFragment
                  true
              }
                R.id.infoUtil -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment)
                        .show(informacionUtilFragment).commit()
                    mActiveFragment = informacionUtilFragment
                    true
                }
                else -> false
            }
        }
    }

    private fun cerrarSesion() {
        val preferences = getSharedPreferences("Detalles", Context.MODE_PRIVATE)
        preferences.edit().clear().apply()
        FedemApplication.rol=""
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun DrawerMenu(){
        val drawerLayout : DrawerLayout = binding.containerMain
        val navView : NavigationView = binding.navView
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.perfil ->
                    startActivity(Intent(this, PerfilActivity::class.java))
                R.id.informacion ->
                    startActivity(Intent(this, InfoContactoActivity::class.java))
                R.id.acerca->
                    startActivity(Intent(this, AcercaActivity::class.java))
                R.id.asistentes->
                    startActivity(Intent(this, AsistentesActivity::class.java))
                R.id.cerrarSesion ->
                    cerrarSesion()
            }
            true
        }
    }


    private fun setUpImageCarrousel(){
        val imagen= mutableListOf<CarouselItem>()
        val carrousel=binding.carousel
        val enlace = mutableListOf<PatrocinadoresEntity>()
        val nombre = mutableListOf<PatrocinadoresEntity>()
        mPatrocinadorViewModel.getPatrocinadores().observe(this){ patrocinadores ->
            for (i in patrocinadores){
                val foto=CarouselItem(i.empresaCif.logo)
                imagen.add(foto)
                enlace.add(i)
                nombre.add(i)
            }
            carrousel.addData(imagen)
        }

        carrousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                val urlEnlace = enlace[position].empresaCif.enlace
                this@MenuActivity.let { it1 ->
                    MaterialAlertDialogBuilder(it1)
                        .setTitle(R.string.dialog_fuera_title)
                        .setPositiveButton(R.string.dialog_delete_confirm) { dialogInterface, i ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlEnlace))
                            startActivity(intent)
                        }
                        .setNegativeButton(R.string.dialog_delete_cancel,null)
                        .show()
                }


            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                val nombreEmpresa = nombre[position].empresaCif.nombre
                Toast.makeText(this@MenuActivity,nombreEmpresa , Toast.LENGTH_SHORT).show()
            }

        }
    }


}

