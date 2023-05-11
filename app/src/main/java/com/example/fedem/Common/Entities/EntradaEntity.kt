package com.example.fedem.Common.Entities

data class EntradaEntity(
    var id: Long = 0,
    var congresoId: Long = 0,
    var codigoQr: String,
    var fechaCompra: String,
    var tipo: String,
    var asistente: AsistenteEntity
) {
}