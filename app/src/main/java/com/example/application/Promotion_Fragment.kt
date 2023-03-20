package com.example.application

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class Promotion_Fragment : Fragment(), foodAdapter.OnItemClickListener {

    // declare recyclerview
    lateinit var promoRecyclerView: RecyclerView
    // declare list array
    lateinit var promoArrayList: ArrayList<FoodItemClass>

    lateinit var promoImageArray : Array<Int>
    var promoNameArray = ArrayList<String>()
    var promoDescArray = ArrayList<String>()
    var promoPriceArray = ArrayList<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_promotion, container, false)

        //set up file to scan
        val scanner = Scanner(resources.openRawResource(R.raw.promo))
        readFile(scanner)

        promoImageArray = arrayOf(
            R.drawable.sweet_tooth_special,
            R.drawable.soup_sandwich,
            R.drawable.croissant_breakfast,
            R.drawable.muffin_madness,
            R.drawable.hamburger_happy_hour,
            R.drawable.soup_salad_lunch
        )

        promoRecyclerView = view.findViewById(R.id.promoRecyclerView)
        promoRecyclerView.layoutManager = LinearLayoutManager(context)
        promoRecyclerView.setHasFixedSize(true)
        promoArrayList = arrayListOf<FoodItemClass>()
        getItemData()


        return view
    }


    private fun getItemData(){
        Log.d("test", promoImageArray.indices.toString())
        for (i in promoImageArray.indices){
            val fooditem = FoodItemClass(promoNameArray[i], promoImageArray[i], promoDescArray[i], promoPriceArray[i])
            promoArrayList.add(fooditem)
        }
        Log.d("test", promoArrayList.size.toString())
        // attach adapter to recyclerview to populate data
        promoRecyclerView.adapter = foodAdapter(promoArrayList, this)

    }

    // scanner
    // to change and place in food array
    private fun readFile(scanner: Scanner){
        while (scanner.hasNextLine()){
            val line= scanner.nextLine()
            val pieces = line.split("\t")
            // add food name and description accordingly
            promoNameArray.add(pieces[0])
            promoDescArray.add(pieces[1])
            promoPriceArray.add((pieces[2]).toFloat())
        }
    }

    // pass data to cart?
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

        fmTransac.replace(R.id.promofragment, frag)
        fmTransac.addToBackStack(null)
        fmTransac.commit()
    }
}