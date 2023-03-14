package com.example.application

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView


class FoodItem_Fragment : Fragment() {

    var input_position: Int? = null
    var input_image: Int? = null
    var input_name : String = ""
    var input_desc : String = ""
    var input_price : Float? = null


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
        foodPrice.text = "Price: $ " + "%.2f".format(input_price)

        //hide nav bar
        val currentFrag = childFragmentManager
        val btmView = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)
        btmView!!.visibility = View.INVISIBLE


        // button to go back
        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener{
            // set the bottom nav back to visible
            btmView!!.visibility = View.VISIBLE
            parentFragmentManager.beginTransaction().remove(this).commit()
        }



        return view
    }




}
