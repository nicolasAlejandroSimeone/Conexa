package com.example.conexa.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "productsAdded")
@Parcelize
data class CartProduct(
    var productId:Int =0,
    var title:String? ="",
    var image:String? ="",
    var quantity:Int? =0,
    var price :Double? = 0.0
):Parcelable
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}