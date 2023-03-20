package com.example.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.widget.SearchView

class Home_Fragment : Fragment(), foodAdapter.OnItemClickListener {
    // declare recyclerview
    private lateinit var newRecyclerView: RecyclerView
    // declare list array
    private lateinit var newArrayList: ArrayList<FoodItemClass>

    lateinit var foodImageArray : Array<Int>
    private var foodNameArray = ArrayList<String>()
    private var foodDescArray = ArrayList<String>()
    private var foodPriceArray = ArrayList<Float>()

    private lateinit var adapter: foodAdapter

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

        val searchView = view.findViewById<SearchView>(R.id.searchBar)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(filteredText: String?): Boolean{
                filterList(filteredText!!)
                return true
            }
        })


        return view

    }


    private fun getItemData(){
        for (i in foodImageArray.indices){
            val fooditem = FoodItemClass(foodNameArray[i], foodImageArray[i], foodDescArray[i], foodPriceArray[i])
            newArrayList.add(fooditem)
        }
        // attach adapter to recyclerv  iew to populate data
        adapter = foodAdapter(newArrayList, this)
        newRecyclerView.adapter = adapter

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

    private fun filterList(query: String){

        if (query!= null){
            var filterArrayList = ArrayList<FoodItemClass>()
            for (item in newArrayList){
                if (item.name.toLowerCase(Locale.ROOT).contains(query)){
                    filterArrayList.add(item)
                }

            }
            if (filterArrayList.isEmpty()){
                Toast.makeText(context, "there is nothing", Toast.LENGTH_SHORT).show()
            }
            else{
                adapter.setFilterText(filterArrayList)
            }
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