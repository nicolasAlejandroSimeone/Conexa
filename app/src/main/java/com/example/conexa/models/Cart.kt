package com.example.conexa.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart(
    var userId: Int? = 0,
    var date:String? = "",
    var products : MutableList<ProductsAdded>? = mutableListOf()
):Parcelable