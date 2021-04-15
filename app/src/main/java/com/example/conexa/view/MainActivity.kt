package com.example.conexa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conexa.R
import com.example.conexa.models.Cart
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product
import com.example.conexa.utils.formatPriceTotal
import com.example.conexa.viewModel.CartViewModel
import com.example.conexa.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<CartViewModel>()
    private var cartList: MutableList<CartProduct> = arrayListOf()
    private lateinit var adapterAddedProducts: ProductAddedAdapter
    private lateinit var mDrawerLayout: DrawerLayout
    private var totalPrice:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer)

        adapterAddedProducts = ProductAddedAdapter(this, cartList)

        cart.setOnClickListener(View.OnClickListener {
            viewModel.getAllAddedProducts()
        })

        findViewById<RecyclerView>(R.id.selectedProduct).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterAddedProducts
            adapterAddedProducts.onItemClick = { item, position ->
                adapterAddedProducts.deleteItem(position)
                viewModel.deleteAddedProduct(item.productId)
                cartList.removeAt(position)
                total.text = getTotal(cartList)
            }

        }

        setObservers()
        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        removeAll.setOnClickListener(View.OnClickListener {
            viewModel.deleteAllProducts()
            mDrawerLayout.closeDrawer(GravityCompat.END)
        })
    }

    private fun setObservers() {
        viewModel.results.observe(this, Observer {
            cartList = it
            total.text = getTotal(cartList)
            adapterAddedProducts.refreshList(cartList)
            if(cartList.isEmpty())
            {
                Toast.makeText(this, "No tienes productos en el carrito", Toast.LENGTH_SHORT).show()
            }else{
                mDrawerLayout.openDrawer(GravityCompat.END)
            }
        })

    }

    private fun getTotal(cartList: MutableList<CartProduct>):String{
        val doubleList = arrayListOf<Double>()
        for (cart in cartList){
            val itemPrice = BigDecimal(cart.price!!).multiply(BigDecimal(cart.quantity!!)).toDouble()
            doubleList.add(itemPrice)
        }
        totalPrice = formatPriceTotal(String.format("%.2f", doubleList.sum()))

        return totalPrice
    }
}