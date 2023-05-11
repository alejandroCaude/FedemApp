package com.example.fedem.Actividades.Model

import com.example.fedem.Common.Entities.ActividadEntity
import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.Common.Utils.Constants
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddActividadService {

    @FormUrlEncoded
    @POST(Constants.POST_AJENO_ACTIVIDAD_PATH)
    suspend fun addActividad(@Field("id_ajeno") ajeno: Long, @Field("id_actividad") actividad: Long): Response<MutableList<ActividadEntity>>

}