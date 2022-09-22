package com.example.pokemon.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.pokemon.databinding.GridViewItemBinding
import com.example.pokemon.network.Pokemon
import com.example.pokemon.network.PokemonList

class PokemonGridAdapter(private val onClickListener: OnCLickListener) : ListAdapter<Pokemon, PokemonGridAdapter.PokemonViewHolder>(DiffCallback) {
    class PokemonViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(pokemon)
        }
        holder.bind(pokemon)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.name == newItem.name
        }

    }

    class OnCLickListener(val clickListener: (pokemon: Pokemon) -> Unit){
        fun onClick(pokemon: Pokemon) = clickListener(pokemon)
    }

}