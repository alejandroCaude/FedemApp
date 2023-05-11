package com.example.fedem.Common.Entities

data class ActividadEntity (
    var id: Long = 0,
    var nombre: String,
    var descripcion: String,
    var horaInicio: String,
    var horaFinal: String,
    var imagen:String
    ) {

        constructor() : this(nombre=" ", descripcion = " ", imagen = " ",horaFinal="",horaInicio="")

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ActividadEntity

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }
    }