package com.example.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class Home_Fragment : Fragment(), foodAdapter.OnItemClickListener {
    // declare recyclerview
    private lateinit var newRecyclerView: RecyclerView
    // declare list array
    private lateinit var newArrayList: ArrayList<FoodItemClass>

    lateinit var foodImageArray : Array<Int>
    private var foodNameArray = ArrayList<String>()
    private var foodDescArray = ArrayList<String>()
    private var foodPriceArray = ArrayList<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //set up file to scan
        val scanner = Scanner(resources.openRawResource(R.raw.menu))
        readFile(scanner)

        foodImageArray = arrayOf(
            R.drawable.chocolatecake,
            R.drawable.croissant,
            R.drawable.hamburger,
            R.drawable.soup,
            R.drawable.muffin
        )


        newRecyclerView = view.findViewById(R.id.homeRecyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<FoodItemClass>()
        getItemData()

        return view

    }


    private fun getItemData(){
        for (i in foodImageArray.indices){
            val fooditem = FoodItemClass(foodNameArray[i], foodImageArray[i], foodDescArray[i], foodPriceArray[i])
            newArrayList.add(fooditem)
        }
        // attach adapter to recyclerview to populate data
        newRecyclerView.adapter = foodAdapter(newArrayList, this)

    }

    // scanner
    // to change and place in food array
    private fun readFile(scanner: Scanner){
        while (scanner.hasNextLine()){
            val line= scanner.nextLine()
            val pieces = line.split("\t")
            // add food name and description accordingly
            foodNameArray.add(pieces[0])
            foodDescArray.add(pieces[1])
            foodPriceArray.add((pieces[2]).toFloat())

        }
    }

    override fun passData(position: Int, image: Int, name: String, desc: String, price: Float) {
        // declaring fragment manager
        val fm = childFragmentManager
        // make data transaction to fragment
        val fmTransac = fm.beginTransaction()
        // declare fragment you want to send
        val frag = FoodItem_Fragment()
        val bundle = Bundle()
        bundle.putInt("input_position", position)
        bundle.putInt("input_image", image)
        bundle.putString("input_name", name)
        bundle.putString("input_desc", desc)
        bundle.putFloat("input_price", price)
        frag.arguments = bundle

        fmTransac.replace(R.id.homefragment, frag)
        fmTransac.addToBackStack(null)
        fmTransac.commit()

    }
}