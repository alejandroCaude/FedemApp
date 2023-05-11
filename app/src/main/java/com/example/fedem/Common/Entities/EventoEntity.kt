package com.example.fedem.Common.Entities

data class EventoEntity (
    var id: Long = 0,
    var nombre: String,
    var descripcion: String,
    var dia: Int,
    var mes: Int,
    var horaInicio: String,
    var horaFin: String,
    var lugar: String,
    var imagen:String
    ) {

        constructor() : this(nombre=" ", descripcion = " ", dia = 0, mes=0, lugar=" ", imagen = " ",horaFin="",horaInicio="")

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as EventoEntity

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }
    }