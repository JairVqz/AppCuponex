package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrincipalUsuarioActivity : AppCompatActivity() {

    private lateinit var nav : BottomNavigationView

    private lateinit var correo : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_usuario)

        correo = (intent.extras!!.getSerializable("correo") as String?).toString()

        nav = findViewById(R.id.navActivityPrincipal)
        nav.setSelectedItemId(R.id.home)

        nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val intent = Intent(this@PrincipalUsuarioActivity,PrincipalUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.promocion -> {
                    val intent = Intent(this@PrincipalUsuarioActivity,PromocionesUsuarioActivity::class.java)
                    startActivity(intent)
                }
                R.id.perfil -> {
                    val intent = Intent(this@PrincipalUsuarioActivity,PerfilUsuarioActivity::class.java)
                    Toast.makeText(this@PrincipalUsuarioActivity,"Correo: ${correo}||",Toast.LENGTH_LONG).show()
                    intent.putExtra("correo", correo)
                    startActivity(intent)
                }
                R.id.salir -> {
                    val intent = Intent(this@PrincipalUsuarioActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }


        }


}