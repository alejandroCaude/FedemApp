package com.example.fedem.Common.Entities

class ComidaEntity (
    var id: Long = 0,
    var nombre: String,
    var tipoComida: String,
    var lugar: String,
    var descripcion: String,
    var imagen:String
    ) {

        constructor() : this(nombre=" ", tipoComida = " ", lugar=" ", descripcion = " ",imagen="")

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ComidaEntity

            if (id != other.id) return false

            return true
        }

        override fun hashCode(): Int {
            return id.hashCode()
        }
    }