package com.example.pokemon.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.convertDecimetersToMeters
import com.example.pokemon.convertHectogramaToKilograma
import com.example.pokemon.network.Pokemon
import com.example.pokemon.network.PokemonApi
import com.example.pokemon.network.pokemoninfo.Stats
import com.example.pokemon.network.pokemoninfo.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(pokemonSelected: Pokemon) : ViewModel() {

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _showProgressBar = MutableLiveData<Boolean>()
    val showProgressBar: LiveData<Boolean>
        get() = _showProgressBar

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _height = MutableLiveData<Double>()
    val height: LiveData<Double>
        get() = _height

    private val _weight = MutableLiveData<Double>()
    val weight: LiveData<Double>
        get() = _weight

    private val _stats = MutableLiveData<List<Stats>>()
    val stats: LiveData<List<Stats>>
        get() = _stats

    private val _types = MutableLiveData<List<Types>>()
    val types: LiveData<List<Types>>
        get() = _types

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _pokemon.value = pokemonSelected
        _showProgressBar.value = true
        getPokemonInfo(_pokemon.value!!)
    }

    fun getPokemonInfo(pokemon: Pokemon) {
        var idPokemon = Integer.parseInt(pokemon.getIdFromUrl())
        coroutineScope.launch {
            var pokemonInfo = PokemonApi.retrofitService.getPokemonInfo(idPokemon)
            try {
                var result = pokemonInfo.await()
                _name.value = result.name.replaceFirstChar { it.uppercase() }
                _height.value = convertDecimetersToMeters(result.height)
                _weight.value = convertHectogramaToKilograma(result.weight)
                _stats.value = result.stats
                _types.value = result.types
                _showProgressBar.value = false
            } catch (t: Throwable) {
                Log.i("DetailViewModel", t.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}