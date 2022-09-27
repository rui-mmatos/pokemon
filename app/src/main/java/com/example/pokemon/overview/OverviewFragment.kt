package com.example.pokemon.overview

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        val adapter = PokemonGridAdapter(PokemonGridAdapter.OnCLickListener {
            viewModel.displayPokemonDetails(it)
        })

        binding.pokemonGrid.adapter = adapter
        binding.pokemonGrid.setHasFixedSize(true)

        val manager = GridLayoutManager(activity, 3)
        binding.pokemonGrid.layoutManager = manager

        viewModel.navigateToSelectedPokemon.observe(this, Observer{
            if(null != it){
                findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it, it.name))
                viewModel.displayPokemonDetailsCompleted()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}

