package com.example.fedem.Login

import com.example.fedem.Common.Entities.AsistenteEntity
import com.example.fedem.Common.Utils.Constants
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST(Constants.LOGIN_PATH)
    suspend fun loginUser(@Field("username") username: String, @Field("password") password: String): AsistenteEntity


}