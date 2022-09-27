package com.example.pokemon

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.pokemon.network.Pokemon
import com.example.pokemon.overview.PokemonGridAdapter
import com.skydoves.progressview.ProgressView


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Pokemon>?) {
    val adapter = recyclerView.adapter as PokemonGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

/*@BindingAdapter("android:cardBackgroundColor")
fun bindBackgroundColor(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).asBitmap().load(imgUri).into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource).generate() { palette ->
                    palette?.let { palette ->
                        val intColor = palette.lightVibrantSwatch?.rgb?:0
                        imgView.setBackgroundColor(intColor)
                    }
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                imgView.setBackgroundColor(Color.WHITE)
            }

        })
    }
}*/


@BindingAdapter("app:typeBackground")
fun CardView.typeBackground(type: String?) {
    if (type != null) {
        var color = when (type) {
            "fire" -> R.drawable.type_fire
            "water" -> R.drawable.type_water
            "electric" -> R.drawable.type_eletric
            "grass" -> R.drawable.type_grass
            "ice" -> R.drawable.type_ice
            "fighting" -> R.drawable.type_fighting
            "poison" -> R.drawable.type_poison
            "ground" -> R.drawable.type_ground
            "flying" -> R.drawable.type_flying
            "psychic" -> R.drawable.type_psychic
            "bug" -> R.drawable.type_bug
            "rock" -> R.drawable.type_rock
            "ghost" -> R.drawable.type_ghost
            "dragon" -> R.drawable.type_dragon
            "dark" -> R.drawable.type_dark
            "steel" -> R.drawable.type_steel
            "fairy" -> R.drawable.type_fairy
            else -> R.drawable.type_unknown
        }
        this.setCardBackgroundColor(ResourcesCompat.getColor(resources, color, null))
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

@BindingAdapter("pokemonName")
fun TextView.pokemonName(string: String){
    text = string.replaceFirstChar { it.uppercase() }
}

@BindingAdapter("app:setProgress")
fun ProgressView.setProgress(int: Int){
    progress = int.toFloat()
}

@BindingAdapter("app:setLabel")
fun ProgressView.setLabel(string: String){
    labelText = string
}