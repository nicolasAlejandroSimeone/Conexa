package com.example.conexa.utils

fun formatPrice(price:Double?) : String{
    return "$${price.toString()}"
}

fun formatPriceTotal(price:String?) : String{
    return "Totat: $$price"
}