package com.example.conexa.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conexa.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val context: Context, private var categoryList:MutableList<String>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var onItemClick:((String) -> Unit)? = null

    fun refreshList(categoryList: MutableList<String>) {
        this.categoryList.clear()
        this.categoryList = categoryList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.txtCategory.text = categoryList[position]
    }

    inner class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){

        var txtCategory: TextView = itemView.category

        init {
            txtCategory.setOnClickListener {
                onItemClick?.invoke(categoryList[adapterPosition])
            }
        }

    }
}