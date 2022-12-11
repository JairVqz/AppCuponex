package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class PromocionesUsuarioActivity : AppCompatActivity() {

    private lateinit var nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promociones_usuario)

        nav = findViewById(R.id.navPromociones)

        nav.setSelectedItemId(R.id.promocion)

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val intent = Intent(this@PromocionesUsuarioActivity,PrincipalUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.promocion -> {
                    val intent = Intent(this@PromocionesUsuarioActivity,PromocionesUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.perfil -> {
                    val intent = Intent(this@PromocionesUsuarioActivity,PerfilUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.salir -> {
                    val intent = Intent(this@PromocionesUsuarioActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
    }

}