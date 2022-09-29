package com.example.pokemon.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.PokemonApiStatus
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

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>>
        get() = _pokemons

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _offset: Int = 0
    private var _limit: Int = 1154

    init {
        getPokemons(_offset, _limit)
    }

    fun getPokemons(offset: Int, limit: Int) {
        _status.value = PokemonApiStatus.LOADING
        coroutineScope.launch {
            var getPokemons = PokemonApi.retrofitService.getPokemons(offset, limit)
            try {
                var result = getPokemons.await()
                _status.value = PokemonApiStatus.DONE
                _pokemons.value = result.results

            } catch (t: Throwable) {
                _status.value = PokemonApiStatus.ERROR
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