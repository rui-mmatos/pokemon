package com.example.pokemon


fun convertHectogramaToKilograma(weight: Int): Double {
    return weight / 10.0
}

fun convertDecimetersToMeters(height: Int): Double{
    return height / 10.0
}

fun capitalizeFirstLetter(string: String): String{
    return string.replaceFirstChar {
        it.uppercase()
    }
}