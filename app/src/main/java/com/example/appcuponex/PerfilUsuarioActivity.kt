package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class PerfilUsuarioActivity : AppCompatActivity() {

    private lateinit var nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        nav = findViewById(R.id.navPerfil)
        nav.setSelectedItemId(R.id.perfil)

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val intent = Intent(this@PerfilUsuarioActivity,PrincipalUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.promocion -> {
                    val intent = Intent(this@PerfilUsuarioActivity,PromocionesUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.perfil -> {
                    val intent = Intent(this@PerfilUsuarioActivity,PerfilUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.salir -> {
                    val intent = Intent(this@PerfilUsuarioActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

    }
}