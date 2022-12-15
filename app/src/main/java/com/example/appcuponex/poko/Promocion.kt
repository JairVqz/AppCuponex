package com.example.appcuponex.poko

class Promocion {

    var idPromocion : Int = 0
    var nombre : String = ""
    var descripcion : String = ""
    var fechaInicio : String = ""
    var fechaTermino : String = ""
    var restricciones : String = ""
    var tipoPromocion : Int = 0
    var porcentaje : String = ""
    var costoPromocion : String = ""
    var categoriaPromocion : Int = 0
    var idEstatus : Int = 0
    var idSucursal : Int = 0
    var fotoPromocion : String = ""

    override fun toString(): String {
        return nombre
    }



}