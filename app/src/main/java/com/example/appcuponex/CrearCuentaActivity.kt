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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)
        supportActionBar!!.hide()

        etCorreo = findViewById(R.id.etCorreoCrearCuenta)
        etPassword = findViewById(R.id.etPasswordCrearCuenta)

        val btnCrearCuentaUsuario = findViewById<Button>(R.id.btnCrearCuentaUsuario)

        btnCrearCuentaUsuario.setOnClickListener {
            verificarCamposCrearCuenta()
        }

    }

    fun verificarCamposCrearCuenta(){
        var camposValidos = true
        var correoTx = etCorreo.text.toString()
        var passwordTx = etPassword.text.toString()
        if(correoTx.isEmpty()){
            etCorreo.setError("Correo requerido")
            camposValidos = false
        }
        if (passwordTx.isEmpty()){
            etPassword.setError("Contraseña requerida")
            camposValidos = false

        }
        if (camposValidos){
            //verficarUsuarioCrearCuenta(correoTx, passwordTx)
            verificar(correoTx,passwordTx)
        }
    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@CrearCuentaActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    fun verificar(correo: String, password: String){
        val intent = Intent(this@CrearCuentaActivity,CrearCuentaComplementoActivity::class.java)
        intent.putExtra("correo", correo)
        intent.putExtra("password", password)
        //Toast.makeText(this@CrearCuentaActivity,"Correo:  $correo ||| password: $password |||",Toast.LENGTH_LONG).show()
        startActivity(intent)
    }


}