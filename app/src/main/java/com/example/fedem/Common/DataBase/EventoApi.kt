package com.example.fedem.Common.DataBase

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class EventoApi  constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: EventoApi? = null
        fun getInstance(context: Context) = INSTANCE ?:
        synchronized(this) {
            INSTANCE ?: EventoApi(context).also { INSTANCE = it }
        }
    }
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

}