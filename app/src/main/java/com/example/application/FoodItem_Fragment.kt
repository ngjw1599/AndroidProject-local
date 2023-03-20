package com.example.application

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel


class FoodItem_Fragment : Fragment() {

    var input_position: Int? = null
    var input_image: Int? = null
    var input_name : String = ""
    var input_desc : String = ""
    var input_price : Float? = null
    var item_amount : Int = 0

    // call the viewmodel class
    lateinit var cartListViewModel : CartViewModel


    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_item, container, false)

        input_position = arguments?.getInt("input_position")
        input_image = arguments?.getInt("input_image")
        input_name = arguments?.getString("input_name").toString()
        input_desc = arguments?.getString("input_desc").toString()
        input_price = arguments?.getFloat("input_price")

        // bind to the required components
        val foodName = view.findViewById<TextView>(R.id.foodName)
        foodName.text = input_name
        val foodImage = view.findViewById<ImageView>(R.id.foodImage)
        input_image?.let { foodImage.setImageResource(it) }
        val foodDesc = view.findViewById<TextView>(R.id.foodDesc)
        foodDesc.text = input_desc
        val foodPrice = view.findViewById<TextView>(R.id.foodPrice)
        // add 2dp to price
        foodPrice.text = "Price: $ ${"%.2f".format(input_price)}"

        // declare cart in here
        cartListViewModel = (activity as MainActivity).getCartListViewModel() as CartViewModel

        //hide nav bar
        val btmView = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        btmView!!.visibility = View.INVISIBLE


        // button to go back
        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener{
            // set the bottom nav back to visible
            btmView.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().remove(this).commit()
        }


        // add / subtract multiple of the same item
        val addMultiple = view.findViewById<Button>(R.id.addMultiple)
        val subMultiple = view.findViewById<Button>(R.id.subMultiple)

        // initial set subtract button to disable
        subMultiple.isEnabled = false
        subMultiple.alpha = 0.4F

        // add items
        addMultiple.setOnClickListener{
            item_amount++
            showAmount(view)
        }
        // minus items
        subMultiple.setOnClickListener {
            if (item_amount == 0) {
                subMultiple.isEnabled = false
                subMultiple.alpha = 0.4F
            } else {
                subMultiple.isEnabled = true
                subMultiple.alpha = 1F
                item_amount--
            }
            showAmount(view)
        }

        // ensure that minimum amount of items is 0
        val amountShown = view.findViewById<TextView>(R.id.foodAmount)
        amountShown.addTextChangedListener{
            if (item_amount == 0) {
                subMultiple.isEnabled = false
                subMultiple.alpha = 0.4F
            } else {
                subMultiple.isEnabled = true
                subMultiple.alpha = 1F
            }
        }

        // add to cart, send total to view cart, pass data
        val addToCartButton = view.findViewById<Button>(R.id.addToCart)
        addToCartButton.setOnClickListener{
            if (item_amount == 0){
                Toast.makeText(context, "There are no items to add", Toast.LENGTH_SHORT).show()
            }
            else{
                addToCart(input_name, input_price, item_amount)
            }
        }

        return view
    }

    // functionality of passing data to the cart list
    private fun addToCart(item_name: String, item_price: Float?, item_amount: Int ){
        val newItemAdded = CartClass(item_name, item_price, item_amount)
        // use the view model to link to the cart list
        cartListViewModel.cartList.add(newItemAdded)
    }

    // set value on app
    private fun showAmount(view: View){
        val amountShown = view.findViewById<TextView>(R.id.foodAmount)
        amountShown.text = "$item_amount"
    }



}
