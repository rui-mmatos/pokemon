package com.example.pokemon.network.pokemoninfo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stats(
    val base_stat: Int,
    val stat: Stat
): Parcelable

@Parcelize
data class Stat(
    val name: String
): Parcelable
