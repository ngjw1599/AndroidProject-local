package com.example.application

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class View_Cart_Fragment : Fragment() {

    // call the viewmodel class that holds the cart list
    private lateinit var cartListViewModel: CartViewModel

    // declare recyclerview
    private lateinit var newRecyclerView: RecyclerView

    private var totalPrice: Float? = null


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_cart, container, false)

        newRecyclerView = view.findViewById(R.id.CartItems)
        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)
        var totalPriceView = view.findViewById<TextView>(R.id.totalPrice);
        // declare cart in here
        cartListViewModel = (activity as MainActivity).getCartListViewModel() as CartViewModel
        // if cart is empty, show message
        if (cartListViewModel.cartList.size == 0) {
        }
        // attach adapter to recyclerview to populate data
        newRecyclerView.adapter = cartAdapter(cartListViewModel.cartList, this)

        // call the cart items from the original main menu / promos
        if (cartListViewModel.cartList.size == 0){
            totalPriceView.text = "$0.00"
        }
        else{
            for (itemDetail in cartListViewModel.cartList){
                totalPrice = totalPrice?.plus((itemDetail.price!! * itemDetail.itemAmount))
            }
            totalPriceView!!.text = "$ ${"%.2f".format(totalPrice)}"
        }

        return view
    }

//    private fun getItemData(cartListViewModel: CartViewModel ) {
//        var totalPrice = cartListViewModel.getTotalPrice()
//     Log.d("getItemData", ""+totalPrice)
//
//    }

    fun setTotalPrice(price: Float) {
        val totalPrice = view?.findViewById<TextView>(R.id.totalPrice)
        totalPrice!!.text = "$ ${"%.2f".format(price)}"
    }

    /*fun mainMenuTotalPrice() {
            // declare total price as a float
            var totalPrice = 0.0f
            if (cartListViewModel.cartList.size == 0){
                totalPrice = 0.0F
            }
            else{
                for (itemDetail in cartListViewModel.cartList){
                    totalPrice += (itemDetail.price!! * itemDetail.itemAmount)
                }
            }

            val totalPriceView = view?.findViewById<TextView>(R.id.totalPrice)
            totalPriceView!!.text = "$ ${"%.2f".format(totalPrice)}"
        }*/

}