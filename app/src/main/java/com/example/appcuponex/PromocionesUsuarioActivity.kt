package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.appcuponex.poko.Promocion
import com.example.appcuponex.util.Constantes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class PromocionesUsuarioActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var nav : BottomNavigationView

    private lateinit var btnC1 : Button
    private lateinit var btnC2 : Button
    private lateinit var btnC3 : Button
    private lateinit var btnC4 : Button
    private lateinit var btnC5 : Button
    private lateinit var spPromociones : Spinner

    var listaPromociones = ArrayList<Promocion>()

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

        btnC1 = findViewById(R.id.btnC1)
        btnC2 = findViewById(R.id.btnC2)
        btnC3 = findViewById(R.id.btnC3)
        btnC4 = findViewById(R.id.btnC4)
        btnC5 = findViewById(R.id.btnC5)
        spPromociones = findViewById(R.id.spPromociones)

        //descargarInfoPromociones()
        btnC1.setOnClickListener{descargarInfoPromociones("201")}
        btnC2.setOnClickListener{descargarInfoPromociones("202")}
        btnC3.setOnClickListener{descargarInfoPromociones("203")}
        btnC4.setOnClickListener{descargarInfoPromociones("204")}
        btnC5.setOnClickListener{descargarInfoPromociones("205")}

    }

    private fun descargarInfoPromociones(idCateg : String){
        /*
        var idCateg = 0

        if (btnC1.isPressed){
            idCateg = 201
            Toast.makeText(this@PromocionesUsuarioActivity, "presionado: "+idCateg, Toast.LENGTH_LONG).show()
        }else if (btnC2.isPressed){
            idCateg = 202
            Toast.makeText(this@PromocionesUsuarioActivity, "presionado: "+idCateg, Toast.LENGTH_LONG).show()
        }else if (btnC3.isPressed){
            idCateg = 203
            Toast.makeText(this@PromocionesUsuarioActivity, "presionado: "+idCateg, Toast.LENGTH_LONG).show()
        }else if (btnC4.isPressed){
            idCateg = 204
            Toast.makeText(this@PromocionesUsuarioActivity, "presionado: "+idCateg, Toast.LENGTH_LONG).show()
        }else if (btnC5.isPressed){
            idCateg = 205
            Toast.makeText(this@PromocionesUsuarioActivity, "presionado: "+idCateg, Toast.LENGTH_LONG).show()
        }
        */
        Ion.with(this@PromocionesUsuarioActivity)
           .load("GET",Constantes.URL_WS+"usuarios/bycategoria/"+idCateg)
           .asString()
           .setCallback{e, result ->
               if (e != null){
                   mostrarAlerta(e.message!!)
               }else{
                   llenarSpinnerPromociones(result)
               }
           }
    }

    private fun llenarSpinnerPromociones(promociones : String){

        val gson = Gson()
        val typePromociones = object : TypeToken<ArrayList<Promocion>> () {}.type
        listaPromociones = gson.fromJson<ArrayList<Promocion>>(promociones,typePromociones)
        val adaptador = ArrayAdapter(this@PromocionesUsuarioActivity,
        android.R.layout.simple_spinner_item,listaPromociones)
        spPromociones.adapter = adaptador
        spPromociones.onItemSelectedListener = this

    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@PromocionesUsuarioActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
        //descargarFotoMedico(listaMedicos.get(p2).idMedico)
    }

    override fun onNothingSelected(p0: AdapterView<*>?){

    }


}