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

    private val _nagivateToSelectedPokemon = MutableLiveData<Pokemon>()
    val navigateToSelectedPokemon: LiveData<Pokemon>
        get() = _nagivateToSelectedPokemon

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _offset: Int = 0
    private var _limit: Int = 20

    init {
        _showProgressBar.value = true
        getPokemons(_offset, _limit)
    }

    fun getPokemons(offset: Int, limit: Int) {
        coroutineScope.launch {
            var getPokemons = PokemonApi.retrofitService.getPokemons(offset, limit)
            try {
                var result = getPokemons.await()
                _showProgressBar.value = false
                _pokemons.value = result.results

            } catch (t: Throwable) {
                Log.i("OverViewModel", t.message.toString())
            }
        }
    }

    fun displayPokemonDetails(pokemon: Pokemon) {
        _nagivateToSelectedPokemon.value = pokemon
    }

    fun displayPokemonDetailsCompleted() {
        _nagivateToSelectedPokemon.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}