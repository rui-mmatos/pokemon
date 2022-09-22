package com.example.pokemon.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.network.Pokemon
import com.example.pokemon.network.PokemonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _showProgressBar.value = true
        getPokemons()
    }

    private fun getPokemons() {
        coroutineScope.launch {
            var getPokemons = PokemonApi.retrofitService.getPokemons(0, 1154)
            try {
                var result = getPokemons.await()
                _showProgressBar.value = false
                _pokemons.value = result.results

            } catch (t: Throwable) {
                Log.i("OverViewModel", t.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}