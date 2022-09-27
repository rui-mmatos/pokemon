package com.example.pokemon.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.network.Pokemon

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val pokemon: Pokemon) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(pokemon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}