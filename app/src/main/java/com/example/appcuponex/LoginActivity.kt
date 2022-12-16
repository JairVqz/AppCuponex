package com.example.appcuponex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.appcuponex.poko.RespuestaLogin
import com.example.appcuponex.util.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class LoginActivity : AppCompatActivity() {

    private lateinit var etCorreo : EditText
    private lateinit var etPassword : EditText

    private var correo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        etCorreo = findViewById(R.id.etCorreoLogin)
        etPassword = findViewById(R.id.etPasswordLogin)

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener{
            verificarCamposLogin()
        }

        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this@LoginActivity,CrearCuentaActivity::class.java)
            startActivity(intent)
        }
    }

    fun verificarCamposLogin(){
        var camposValidos = true
        val correoTx = etCorreo.text.toString()
        val passwordTx = etPassword.text.toString()
        if(correoTx.isEmpty()){
            etCorreo.setError("Correo requerido")
            camposValidos = false
        }
        if (passwordTx.isEmpty()){
            etPassword.setError("Contraseña requerida")
            camposValidos = false
        }
        if (camposValidos){
            verificarUsuarioSesion(correoTx, passwordTx)
        }
    }

    fun verificarUsuarioSesion(correo : String, password : String){
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
        Ion.with(this@LoginActivity)
            .load("POST", Constantes.URL_WS+"acceso/movil")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("correo",correo)
            .setBodyParameter("password",password)
            .asString()
            .setCallback { e, result ->
                if(e != null){
                    mostrarAlerta("Error de conexión...")
                }else{
                    validarRespuesta(result)
                }
            }
    }

    private fun validarRespuesta(resultado: String){
        val gson = Gson()
        val respuestaWS = gson.fromJson(resultado, RespuestaLogin::class.java)
        if (respuestaWS.error!!){
            mostrarAlerta(respuestaWS.mensaje)
        }else{
            //Credenciales correctas
            mostrarAlerta("Bienvenido: "+respuestaWS.nombre)
            val intent = Intent(this@LoginActivity,PrincipalUsuarioActivity::class.java)
            //Toast.makeText(this@LoginActivity,"Correo: ${etCorreo.text.toString()}||",Toast.LENGTH_LONG).show()
            intent.putExtra("correo",etCorreo.text.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@LoginActivity, mensaje, Toast.LENGTH_LONG).show()
    }

}