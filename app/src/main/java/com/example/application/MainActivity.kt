package com.example.application

import android.app.assist.AssistStructure.ViewNode
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList
import androidx.fragment.app.viewModels

class MainActivity : AppCompatActivity(){
    /*// declare recyclerview
    private lateinit var newRecyclerView: RecyclerView
    // declare list array
    private lateinit var newArrayList: ArrayList<FoodItemClass>

    lateinit var foodImageArray : Array<Int>
    private var foodNameArray = ArrayList<String>()
    private var foodDescArray = ArrayList<String>()

*/
    // view model declaration in main activity as a form of global variable
    private val cartListViewModel : CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val home = Home_Fragment()
        val promotion = Promotion_Fragment()
        val cart = View_Cart_Fragment()

        // shifted all main activity to another home fragment
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, home).commit()

        // navigation of the bottom nav bar
        val btmnav = findViewById<BottomNavigationView>(R.id.bottomNav)
        btmnav.setOnNavigationItemSelectedListener() {
            // itemid is the id being set in the nav_menu.xml
            when (it.itemId) {
                R.id.ic_home ->
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout, home)
                        .commit()
                R.id.ic_promo ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, promotion).commit()
                R.id.ic_checkout ->
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout, cart)
                        .commit()
            }
            true
        }

        getCartListViewModel()


    }

    // get the cart view model
    fun getCartListViewModel(): ViewModel{
        return cartListViewModel
    }



}