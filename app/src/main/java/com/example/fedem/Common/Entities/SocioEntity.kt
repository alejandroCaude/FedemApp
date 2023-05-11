package com.example.fedem.Common.Entities

data class SocioEntity(
    var idSocio: Long = 0,
    var cargo :String,
    var asistente: AsistenteEntity
)