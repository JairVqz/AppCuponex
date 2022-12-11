package com.example.appcuponex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appcuponex.util.Constantes
import com.koushikdutta.ion.Ion

class CrearCuentaActivity : AppCompatActivity() {

    private lateinit var etCorreo : EditText
    private lateinit var etPassword : EditText
    private lateinit var etPasswordConfirm : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        supportActionBar!!.hide()

        etCorreo = findViewById(R.id.etCorreoCrearCuenta)
        etPassword = findViewById(R.id.etPasswordCrearCuenta)
        etPasswordConfirm = findViewById(R.id.etConfirmarPasswordCrearCuenta)

        val btnCrearCuentaUsuario = findViewById<Button>(R.id.btnCrearCuentaUsuario)

        btnCrearCuentaUsuario.setOnClickListener {
            verificarCamposCrearCuenta()
            //val intent = Intent(this@CrearCuentaActivity,CrearCuentaComplementoActivity::class.java)
            //startActivity(intent)
        }

    }

    fun verificarCamposCrearCuenta(){
        var camposValidos = true
        var correoTx = etCorreo.text.toString()
        var passwordTx = etPassword.text.toString()
        var passwordConfirmTx = etPasswordConfirm.text.toString()
        if(correoTx.isEmpty()){
            etCorreo.setError("Correo requerido")
            camposValidos = false
        }
        if (passwordTx.isEmpty() || passwordConfirmTx.isEmpty()){
            etPassword.setError("Contraseña requerida")
            camposValidos = false
        }
        if (camposValidos){
            verficarUsuarioCrearCuenta(correoTx, passwordTx)
        }
    }

    fun verficarUsuarioCrearCuenta(correo: String, password : String){
        Ion.getDefault(this@CrearCuentaActivity).conscryptMiddleware.enable(false)
        Ion.with(this@CrearCuentaActivity)
            .load("POST", Constantes.URL_WS+"usuarios/registrar")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("correo",correo)
            .setBodyParameter("password",password)
            .asString()
            .setCallback { e, result ->
                if (e != null){
                    mostrarAlerta("Error de conexión")
                }else{
                    val intent = Intent(this@CrearCuentaActivity,CrearCuentaComplementoActivity::class.java)
                    intent.putExtra("correo", correo)
                    startActivity(intent)
                }
            }
    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@CrearCuentaActivity, mensaje, Toast.LENGTH_LONG).show()
    }


}