package com.example.pokemon.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonList(
    val results: List<Pokemon>
) : Parcelable

@Parcelize
data class Pokemon(
    val name: String,
    val url: String
): Parcelable{
    val imgUrl: String
        get()= "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getIdFromUrl()}.png"
            fun getIdFromUrl(): String{
                val urlParts = url.split("/")
                return urlParts[urlParts.lastIndex-1]
            }
}