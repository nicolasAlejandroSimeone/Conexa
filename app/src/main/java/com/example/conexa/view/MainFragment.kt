package com.example.conexa.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conexa.R
import com.example.conexa.models.Cart
import com.example.conexa.models.CartProduct
import com.example.conexa.models.Product
import com.example.conexa.models.ProductsAdded
import com.example.conexa.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.AllPermission
import java.util.*

class MainFragment : Fragment() {

    companion object{
        private const val ALL = "All"
    }

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapterProduct: ProductAdapter
    private lateinit var adapterCategories: CategoryAdapter
    private var productList: MutableList<Product> = arrayListOf()
    private var categoryList: MutableList<String> = arrayListOf()
    private var addedProductList: MutableList<ProductsAdded> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_main, container, false)

        adapterProduct = ProductAdapter(requireContext(), productList)
        adapterCategories = CategoryAdapter(requireContext(), categoryList)

        view.findViewById<RecyclerView>(R.id.product_recycler_view).apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = adapterProduct
            adapterProduct.onItemClick = {
                product , quantity->

                val productCart = ProductsAdded()
                productCart.productId = product.id
                productCart.quantity =  quantity
                addedProductList.add(productCart)


                val productAdded = Cart()
                productAdded.date = Calendar.getInstance().toString()
                productAdded.userId = 10
                productAdded.products = addedProductList

                val productDb = CartProduct()
                productDb.productId = product.id!!
                productDb.title = product.title
                productDb.price = product.price
                productDb.image = product.image
                productDb.quantity = quantity

                viewModel.insertAddedProducts(productDb)
                Toast.makeText(requireContext(), "Producto Añadido al carrito", Toast.LENGTH_SHORT).show()
                //viewModel.addToCart(productAdded)
            }

        }

        view.findViewById<RecyclerView>(R.id.catergory_recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterCategories
            adapterCategories.onItemClick = {
                item ->
                if (item == ALL) {
                    viewModel.getAllProducts()
                    view.product_recycler_view.visibility = View.GONE
                    view.progressBar.visibility = View.VISIBLE
                    view.search.setText("")
                } else {
                    view.product_recycler_view.visibility = View.GONE
                    view.progressBar.visibility = View.VISIBLE
                    viewModel.getAllProductsFiltered(item)
                    view.search.setText("")
                }
            }

        }

        view.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.getAllProducts()
                view.product_recycler_view.visibility = View.GONE
                view.progressBar.visibility = View.VISIBLE
            }
            false
        }

        setObservers()

        viewModel.getAllCategories()

        return view
    }

    private fun setObservers() {
        viewModel.results.observe(viewLifecycleOwner, Observer {
            if (requireView().search.text.isNullOrEmpty()){
                productList = it
                adapterProduct.refreshList(productList)
                progressBar.visibility = View.GONE
                mainFragment.visibility = View.VISIBLE
                product_recycler_view.visibility = View.VISIBLE
            }else{
                productList = it.filter { s -> s.title!!.toLowerCase().contains(requireView().search.text.toString().toLowerCase()) }.toMutableList()
                adapterProduct.refreshList(productList)
                progressBar.visibility = View.GONE
                mainFragment.visibility = View.VISIBLE
                product_recycler_view.visibility = View.VISIBLE
            }
        })

        viewModel.resultsCategories.observe(viewLifecycleOwner, Observer {
            categoryList = it
            categoryList.add(0, ALL)
            adapterCategories.refreshList(categoryList)
            viewModel.getAllProducts()
        })

        viewModel.resultsFiltered.observe(viewLifecycleOwner, Observer {
            productList = it
            adapterProduct.refreshList(productList)
            progressBar.visibility = View.GONE
            product_recycler_view.visibility = View.VISIBLE
        })
    }
}