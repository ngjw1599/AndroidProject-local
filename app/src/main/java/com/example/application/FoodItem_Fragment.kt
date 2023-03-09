package com.example.application

import android.content.Intent
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
import androidx.fragment.app.FragmentManager


class FoodItem_Fragment : Fragment() {

    var input_position: Int? = null
    var input_image: Int? = null
    var input_name : String = ""
    var input_desc : String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_item_, container, false)

        input_position = arguments?.getInt("input_position")
        input_image = arguments?.getInt("input_image")
        input_name = arguments?.getString("input_name").toString()
        input_desc = arguments?.getString("input_desc").toString()

        // bind to the required components
        val foodName = view.findViewById<TextView>(R.id.foodName)
        foodName.text = input_name
        val foodImage = view.findViewById<ImageView>(R.id.foodImage)
        input_image?.let { foodImage.setImageResource(it) }
        val foodDesc = view.findViewById<TextView>(R.id.foodDesc)
        foodDesc.text = input_desc

        // button to go back
        val backButton = view.findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener{
                //val main = MainActivity()
                //Log.d("test", "onBackClick: hi")

                //val transac = parentFragmentManager.beginTransaction()
                //parentFragmentManager.popBackStackImmediate()
                //transac.replace(R.id.fragmentitem, main)
//              //transac.commit()

            // remove current stacked fragement
            parentFragmentManager.beginTransaction().remove(this).commit()
        }


        return view
    }

}
