package com.example.conexa.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsAdded(
    var productId: Int? = 0,
    var quantity: Int? = 0
):Parcelable