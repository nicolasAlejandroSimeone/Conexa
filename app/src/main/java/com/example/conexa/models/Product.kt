package com.example.conexa.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    val id: Int? = 0,
    val title : String? = "",
    val price : Double? = 0.0,
    val description : String? = "",
    val category : String? ="",
    val image: String? ="",
    val quantity:Int? = 0
):Parcelable