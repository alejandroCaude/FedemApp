package com.example.fedem.Common.Utils

object Constants {
    const val API_URL = "http://apicei58.ieslasenia.org"
    const val GET_ALL_EVENTOS_PATH = "/eventos"
    const val GET_EVENTO_PATH = "/evento/{id}"
    const val GET_ALL_ACTIVIDADES_PATH = "/actividades"
    const val GET_ACTIVIDAD_PATH = "/actividad/{id}"
    const val GET_ACTIVIDADES_AJENO_PATH = "/ajeno/{id_ajeno}/actividades"
    const val GET_ALL_COMIDAS_PATH = "/puestosComida"
    const val GET_COMIDA_PATH = "/puestoComida/{id}"
    const val GET_BONOS_ASISTENTES_PATH = "/asistente/{id_asistente}/bonos"
    const val GET_ALL_ASISTENTES_PATH = "/asistentes"
    const val GET_ALL_PATROCINADORES_PATH = "/patrocinadores"
    const val GET_ENTRADA_PATH = "/asistente/{id}/entrada"
    const val GET_SOCIO_PATH = "/asistente/{id}/socio"
    const val POST_AJENO_ACTIVIDAD_PATH = "/ajeno/{id_ajeno}/actividad/{id_actividad}"
    const val DELETE_AJENO_ACTIVIDAD_PATH = "/ajeno/{id_ajeno}/actividad/{id_actividad}"

    const val SUCCESS = 1
    const val ERROR = 2

//LOGIN
    const val LOGIN_PATH = "/logear"

    const val EMAIL_PARAM = "username"
    const val PASSWORD_PARAM = "password"
}