package com.example.fedem.Common.Entities

data class PatrocinadoresEntity(
    var id: Long = 0,
    var empresaCif:EmpresaEntity
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PatrocinadoresEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}