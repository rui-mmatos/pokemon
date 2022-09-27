package com.example.pokemon.network.pokemoninfo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Types(
    val type: Type
): Parcelable

@Parcelize
data class Type(
    val name: String
): Parcelable
