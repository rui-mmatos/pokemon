package com.example.pokemon

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

class Palette{
    companion object{
        fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()
    }
}