package com.example.conexa.view

import android.content.Context
import android.icu.number.IntegerWidth
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.conexa.R
import com.example.conexa.models.Product
import com.example.conexa.utils.formatPrice
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val context: Context, private var productList:MutableList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var onItemClick:((Product, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.txtTitle.text = productList[position].title
        holder.txtPrice.text = formatPrice(productList[position].price)



        Glide.with(this.context)
            .load(productList[position].image)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .into(holder.imgView)
    }

    fun refreshList(productList: MutableList<Product>) {
        this.productList.clear()
        this.productList = productList.toMutableList()
        notifyDataSetChanged()
    }


    inner class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        var txtTitle: TextView = itemView.title
        var imgView: ImageView = itemView.imgView
        var txtCount : EditText = itemView.count
        var txtPrice : TextView = itemView.price
        var txtAddCart : TextView = itemView.addItem

        init {
            txtAddCart.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition], txtCount.text.toString().toInt())
            }
        }

    }
}