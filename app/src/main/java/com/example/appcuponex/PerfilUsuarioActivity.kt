package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.example.appcuponex.poko.Usuario
import com.example.appcuponex.util.Constantes
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import java.util.*

class PerfilUsuarioActivity : AppCompatActivity() {

    private lateinit var nav : BottomNavigationView

    private lateinit var etNombrePerfilUsuario : EditText
    private lateinit var etApellidoPaternoPerfilUsuario : EditText
    private lateinit var etApellidoMaternoPerfilUsuario : EditText
    private lateinit var etTelefonoPerfilUsuario : EditText
    private lateinit var etDireccionPerfilUsuario : EditText
    private lateinit var etPasswordPerfilUsuario : EditText
    private lateinit var  dpFechaNacimiento : DatePicker

    private lateinit var  btnGuardarCambiosPerfilUsuario : Button

    private var correo = ""

    private var idUsuario : Int = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        correo = (intent.extras!!.getSerializable("correo") as String?).toString()

        etNombrePerfilUsuario = findViewById(R.id.etNombrePerfilUsuario)
        etApellidoPaternoPerfilUsuario = findViewById(R.id.etApellidoPaternoPerfilUsuario)
        etApellidoMaternoPerfilUsuario = findViewById(R.id.etApellidoMaternoPerfilUsuario)
        etTelefonoPerfilUsuario = findViewById(R.id.etTelefonoPerfilUsuario)
        etDireccionPerfilUsuario = findViewById(R.id.etDireccionPerfilUsuario)
        etPasswordPerfilUsuario = findViewById(R.id.etPasswordPerfilUsuario)
        dpFechaNacimiento = findViewById(R.id.dpFechaNacimientoAct)

        btnGuardarCambiosPerfilUsuario = findViewById(R.id.btnGuardarCambiosPerfilUsuario)

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

        cargarInformacionUsuarios(correo)

        btnGuardarCambiosPerfilUsuario.setOnClickListener{
            actualizarDatos()
        }

    }

    private fun cargarInformacionUsuarios(correo : String){

        Ion.getDefault(this@PerfilUsuarioActivity).conscryptMiddleware.enable(false)
        Ion.with(this@PerfilUsuarioActivity).load("POST",Constantes.URL_WS+"usuarios/obtenerUsuario")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
            .setBodyParameter("correo",correo)
            .asString()
            .setCallback{e,result ->
                if (e!= null) {
                    mostrarAlerta("Error de conexión")
                }else{
                    llenarInfoUsuario(result)
                }
            }


    }

    private fun llenarInfoUsuario(usuarios: String){

        val gson = Gson()
        val infoUsuario = gson.fromJson(usuarios,Usuario::class.java)

        idUsuario = infoUsuario.idUsuario

        etNombrePerfilUsuario.setText(infoUsuario.nombre)
        etApellidoPaternoPerfilUsuario.setText(infoUsuario.apellidoPaterno)
        etApellidoMaternoPerfilUsuario.setText(infoUsuario.apellidoMaterno)
        etTelefonoPerfilUsuario.setText(infoUsuario.telefono)
        etDireccionPerfilUsuario.setText(infoUsuario.direccion)
        etPasswordPerfilUsuario.setText(infoUsuario.password)

        var defaultDate = infoUsuario.fechaNacimiento.toString().split(Regex("-"))
        var dd = defaultDate[2].toInt()
        var mm = defaultDate[1].toInt()-1
        var yy = defaultDate[0].toInt()

        dpFechaNacimiento.updateDate(yy,mm,dd)

        Toast.makeText(this@PerfilUsuarioActivity,"ID:${infoUsuario.idUsuario} || \n${infoUsuario.nombre}||${infoUsuario.apellidoPaterno}||${infoUsuario.apellidoMaterno}||" +
                "${infoUsuario.telefono}||${infoUsuario.direccion}||${infoUsuario.password}"
            ,Toast.LENGTH_LONG).show()



    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@PerfilUsuarioActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    fun actualizarDatos(){

        var nombre = etNombrePerfilUsuario.text.toString()
        var apellidoPaterno = etApellidoPaternoPerfilUsuario.text.toString()
        var apellidoMaterno = etApellidoMaternoPerfilUsuario.text.toString()
        var telefono = etTelefonoPerfilUsuario.text.toString()
        var direccion = etDireccionPerfilUsuario.text.toString()
        var password = etPasswordPerfilUsuario.text.toString()

        val fechaNacimiento = Calendar.getInstance()
        var fecha =""
        //Toast.makeText(this@PerfilUsuarioActivity,"en actualizar datosID:${idUsuario} ||"
        //    ,Toast.LENGTH_LONG).show()

        dpFechaNacimiento.init(fechaNacimiento.get(Calendar.YEAR), fechaNacimiento.get(Calendar.MONTH), fechaNacimiento.get(
            Calendar.DAY_OF_MONTH))

        { view, year, month, day ->
            val month = month + 1
            val msg = "$year/$month/$day"
            //fecha = "$year/$month/$day"

            Toast.makeText(this@PerfilUsuarioActivity,"||Lamdafecha: ${msg}"    ,Toast.LENGTH_LONG).show()
            consumirModificar(idUsuario,nombre,apellidoPaterno,apellidoMaterno,telefono,direccion,msg,password)

        }

    }

    fun consumirModificar(idUsuario:Int,nombre:String,apellidoPaterno:String,apellidoMaterno:String,
                               telefono:String,direccion:String,fechaNacimiento:String,password:String
                              ){

        Ion.getDefault(this@PerfilUsuarioActivity).conscryptMiddleware.enable(false)

        Ion.with(this@PerfilUsuarioActivity)
            .load("PUT", Constantes.URL_WS+"usuarios/modificar")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("idUsuario", idUsuario.toString())
            .setBodyParameter("nombre", nombre)
            .setBodyParameter("apellidoPaterno", apellidoPaterno)
            .setBodyParameter("apellidoMaterno", apellidoMaterno)
            .setBodyParameter("telefono", telefono.toString())
            .setBodyParameter("direccion", direccion)
            .setBodyParameter("fechaNacimiento", fechaNacimiento)
            .setBodyParameter("password", password)
            .asString()
            .setCallback { e, result ->
                if (e != null){
                    mostrarAlerta("Error de conexión"+e.message)
                }else{
                    Toast.makeText(this@PerfilUsuarioActivity,"Datos Actualizados",Toast.LENGTH_LONG).show()
                }

            }

    }



}