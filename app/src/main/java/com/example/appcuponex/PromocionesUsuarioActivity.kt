package com.example.appcuponex

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
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

    private lateinit var tvTituloPromocion : TextView
    private lateinit var tvVigenciaPromocion : TextView
    private lateinit var tvTipoPromocion : TextView
    private lateinit var tvEmpresaPromocion : TextView
    private lateinit var ivFotoPromocion : ImageView

    private lateinit var tvDescripcionPromocion : TextView
    private lateinit var tvRestriccionPromocion : TextView
    private lateinit var tvPorcentajePromocion : TextView

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

        tvTituloPromocion = findViewById(R.id.tvTituloPromocion)
        tvVigenciaPromocion = findViewById(R.id.tvVigenciaPromocion)
        tvTipoPromocion = findViewById(R.id.tvTipoPromocion)
        tvEmpresaPromocion = findViewById(R.id.tvEmpresaPromocion)
        ivFotoPromocion = findViewById(R.id.ivFotoPromocion)

        tvDescripcionPromocion = findViewById(R.id.tvDescripcionPromocion)
        tvRestriccionPromocion = findViewById(R.id.tvRestriccionPromocion)
        tvPorcentajePromocion = findViewById(R.id.tvPorcentajePromocion)

        //descargarInfoPromociones()
        btnC1.setOnClickListener{descargarInfoPromociones("201")}
        btnC2.setOnClickListener{descargarInfoPromociones("202")}
        btnC3.setOnClickListener{descargarInfoPromociones("203")}
        btnC4.setOnClickListener{descargarInfoPromociones("204")}
        btnC5.setOnClickListener{descargarInfoPromociones("205")}

    }

    private fun descargarInfoPromociones(idCateg : String){

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

    private fun descargarFotoPromocion(idPromocion : Int){
        Ion.with(this@PromocionesUsuarioActivity)
           //.load("GET",Constantes.URL_WS+"obtenerFoto/"+idPromocion)
           .load("GET",Constantes.URL_WS+"usuarios/obtenerFoto/"+idPromocion)
           .asString()
           .setCallback{e,result->
               if( e!= null ){
                   mostrarAlerta(e.message!!)
               }else{
                   //Toast.makeText(this@PromocionesUsuarioActivity,"result: "+result+"|||",Toast.LENGTH_LONG).show()
                   cargarInformacion(result)
               }
           }
    }

    private fun cargarInformacion(informacion : String){
        val gson = Gson()
        val infoPromo = gson.fromJson(informacion,Promocion::class.java)

        tvTituloPromocion.text = "${infoPromo.nombre}"
        tvVigenciaPromocion.text = "${infoPromo.fechaInicio} - ${infoPromo.fechaTermino}"
        //tvTipoPromocion.text = "Tipo: ${infoPromo.tipoPromocion}"
        tvTipoPromocion.text = "${infoPromo.nombreTipoPromocion}"
        tvEmpresaPromocion.text = "${infoPromo.nombreEmpresa}"

        tvDescripcionPromocion.text = "${infoPromo.descripcion}"
        tvRestriccionPromocion.text = "${infoPromo.restricciones}"
        tvPorcentajePromocion.text = "${infoPromo.porcentaje}"

        //Conversión de imagen
        val byteImg = Base64.decode(infoPromo.fotoPromocion, Base64.DEFAULT)
        val bitMapImg = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.size)
        ivFotoPromocion.setImageBitmap(bitMapImg)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
        //descargarFotoMedico(listaMedicos.get(p2).idMedico)
        //Toast.makeText(this@PromocionesUsuarioActivity,"onItemS"+listaPromociones.get(p2).idPromocion,Toast.LENGTH_LONG).show()
        descargarFotoPromocion(listaPromociones.get(p2).idPromocion)
    }

    override fun onNothingSelected(p0: AdapterView<*>?){

    }


}