package com.example.fedem.Common.Entities

data class EmpresaEntity(
    var id: Long = 0,
    var nombre: String,
    var direccion: String,
    var logo: String,
    var cif: String,
    var enlace: String
) {
    constructor() : this(nombre=" ", direccion = " ", logo=" ", cif = " ",enlace="")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmpresaEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}