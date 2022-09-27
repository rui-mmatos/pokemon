package com.example.pokemon.network.pokemoninfo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<Stats>,
    val types: List<Types>
) : Parcelable


