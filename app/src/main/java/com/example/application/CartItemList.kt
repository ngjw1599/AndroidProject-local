package com.example.application

// not used

class CartItemList {
    private val cartItems = mutableListOf<CartClass>()

    // add item to cart
    fun addItem(item: CartClass){
        val existingItem = cartItems.find { it.name == item.name }
        if (existingItem != null){
            existingItem.itemAmount += item.itemAmount
        }
        else{
            cartItems.add(item)
        }

    }

    fun removeItem(item: CartClass){
        cartItems.remove(item)
    }

    fun getItems(): List<CartClass>{
        return cartItems
    }

}