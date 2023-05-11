package com.example.fedem.MenuPrincipal.Adapter


import androidx.lifecycle.LifecycleOwner
import com.example.fedem.Common.Entities.PatrocinadoresEntity

interface OnClickListener {
    abstract val viewLifecycleOwner: LifecycleOwner

    fun onClick(patrocinadoresEntity: PatrocinadoresEntity)
}