package com.example.conexa.view

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.conexa.R
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product
import com.example.conexa.utils.formatPrice
import kotlinx.android.synthetic.main.item_added.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.price
import org.w3c.dom.Text

class ProductAddedAdapter(private val context: Context, private var productList:MutableList<CartProduct>): RecyclerView.Adapter<ProductAddedAdapter.ViewHolder>()  {

    var onItemClick:((CartProduct,Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAddedAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_added, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductAddedAdapter.ViewHolder, position: Int) {
        holder.title.text = productList[position].title
        holder.txtPrice.text = formatPrice(productList[position].price)
        holder.quantity.text = productList[position].quantity.toString()

        Glide.with(this.context)
            .load(productList[position].image)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .into(holder.imgView)
    }

    fun refreshList(productList: MutableList<CartProduct>) {
        this.productList.clear()
        this.productList = productList.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int){
        productList.removeAt(index)
        notifyDataSetChanged()
    }


    inner class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        var imgView: ImageView = itemView.imgViewAdded
        var title: TextView = itemView.titleAdded
        var quantity : TextView = itemView.quantity
        var txtPrice : TextView = itemView.priceAdded
        var deleteView : ImageView = itemView.delete

        init {
            deleteView.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition], adapterPosition)
            }
        }

    }
}