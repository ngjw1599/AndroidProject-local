package com.example.application

import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel() {
    val cartList = mutableListOf<CartClass>()

//    fun getTotalPrice(): Float {
//        var totalPrice = 0.0f;
//        for(i in cartList){
//            totalPrice += i.price!!*i.itemAmount!!
//        }
//        return totalPrice
//    }

}