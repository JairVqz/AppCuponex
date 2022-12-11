package com.example.appcuponex

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appcuponex.util.Constantes
import com.koushikdutta.ion.Ion
import java.util.Calendar

class CrearCuentaComplementoActivity : AppCompatActivity() {

    private lateinit var etCorreo : TextView
    private lateinit var etNombre : EditText
    private lateinit var etApellidoPaterno : EditText
    private lateinit var etApellidoMaterno : EditText
    private lateinit var etTelefono : EditText
    private lateinit var etDireccion : EditText
    private lateinit var  dpFechaNacimiento : DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta_complemento)

        val correo = intent.extras!!.getSerializable("correo") as String?
        etCorreo = findViewById(R.id.tvCorreoCrearCuentaComplemento)
        etCorreo.append("" + correo)

        etNombre = findViewById(R.id.etNombreCrearCuentaComplemento)
        etApellidoPaterno = findViewById(R.id.etApellidoPaternoCrearCuentaComplemento)
        etApellidoMaterno = findViewById(R.id.etApellidoMaternoCrearCuentaComplemento)
        etTelefono = findViewById(R.id.etTelefonoCrearCuentaComplemento)
        etDireccion = findViewById(R.id.etDireccionCrearCuentaComplemento)

        dpFechaNacimiento = findViewById(R.id.dpFechaNacimientoCrearCuentaComplemento)


        val btnCrearCuentaComplemento = findViewById<Button>(R.id.btnCrearCuentaUsuarioComplemento)

        btnCrearCuentaComplemento.setOnClickListener {
            verificarCamposCrearCuenta()
        }

    }

    fun verificarCamposCrearCuenta(){
        var camposValidos = true
        var correoTx = etCorreo.text.toString()
        var nombreTx = etNombre.text.toString()
        var apellidoPaternoTx = etApellidoPaterno.text.toString()
        var apellidoMaternoTx = etApellidoMaterno.text.toString()


        if (nombreTx.isEmpty()){
            etNombre.setError("Nombre requerido")
            camposValidos = false
        }
        if (apellidoPaternoTx.isEmpty()){
            etApellidoPaterno.setError("Apellido paterno requerido")
            camposValidos = false
        }
        if (apellidoMaternoTx.isEmpty()){
            etApellidoMaterno.setError("Apellido materno requerido")
            camposValidos = false
        }
        if (camposValidos){
            val fechaNacimiento = Calendar.getInstance()
            dpFechaNacimiento.init(fechaNacimiento.get(Calendar.YEAR), fechaNacimiento.get(Calendar.MONTH), fechaNacimiento.get(Calendar.DAY_OF_MONTH))

            { view, year, month, day ->
                val month = month + 1
                val msg = "$year/$month/$day"
                verificarUsuarioCrearCuenta(correoTx, nombreTx, apellidoPaternoTx, apellidoMaternoTx, msg)
            }

        }

    }

    fun verificarUsuarioCrearCuenta(correo : String, nombre: String, apellidoPaterno : String, apellidoMaterno : String, fechaNacimiento : String){
        Ion.getDefault(this@CrearCuentaComplementoActivity).conscryptMiddleware.enable(false)
        Ion.with(this@CrearCuentaComplementoActivity)
            .load("POST", Constantes.URL_WS+"usuarios/registrarComplemento")
            .setHeader("Content-Type", "application/x-www-form-urlencoded")
            .setBodyParameter("correo", correo)
            .setBodyParameter("nombre", nombre)
            .setBodyParameter("apellidoPaterno", apellidoPaterno)
            .setBodyParameter("apellidoMaterno", apellidoMaterno)
            .setBodyParameter("fechaNacimiento", fechaNacimiento)
            .asString()
            .setCallback { e, result ->
                if (e != null){
                    mostrarAlerta("Error de conexión"+e.message)
                }else{
                val intent = Intent(this@CrearCuentaComplementoActivity,PrincipalUsuarioActivity::class.java)
                //intent.putExtra("correo", correo)
                startActivity(intent)
                }

            }
    }

    private fun mostrarAlerta(mensaje: String){
        Toast.makeText(this@CrearCuentaComplementoActivity, mensaje, Toast.LENGTH_LONG).show()
    }

}