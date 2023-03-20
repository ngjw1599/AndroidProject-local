package com.example.application

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class cartAdapter(private val cartList: MutableList<CartClass>, private val fragment: View_Cart_Fragment): RecyclerView.Adapter<cartAdapter.MyViewHolder>(){
    inner class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val foodTextView = itemView.findViewById<TextView>(R.id.cartFoodItem)
        val priceTextView = itemView.findViewById<TextView>(R.id.cartFoodPrice)
        val amountTextView = itemView.findViewById<TextView>(R.id.cartFoodAmount)
        val removeButton = itemView.findViewById<Button>(R.id.subMultiple)
        val addButton = itemView.findViewById<Button>(R.id.addMultiple)

        init{
            fragment.setTotalPrice(totalPriceCalculate())
            // on click to add
            addButton.setOnClickListener{
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val cartItem = cartList[position]
                    cartItem.itemAmount++
                    amountTextView.text = cartItem.itemAmount.toString()
                    notifyItemChanged(position)
                    fragment.setTotalPrice(totalPriceCalculate())
                    Log.d(TAG, ""+cartList.toString())
                }

            }
            removeButton.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val cartItem = cartList[position]
                    if (cartItem.itemAmount > 1) {
                        cartItem.itemAmount--
                        amountTextView.text = cartItem.itemAmount.toString()
                        fragment.setTotalPrice(totalPriceCalculate())
                    } else {
                        cartList.removeAt(position)
                        notifyItemChanged(position)
                        fragment.setTotalPrice(totalPriceCalculate())
                    }
                }
            }

        }


    }

    // inflate in cart list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.cartlist_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = cartList[position]
        holder.foodTextView.text = currentItem.name
        // toString the ones with non text values
        holder.priceTextView.text = "$ ${"%.2f".format(currentItem.price)}"
        holder.amountTextView.text = currentItem.itemAmount.toString()

    }

    private fun totalPriceCalculate(): Float {
        // declare total price as a float
        var totalPrice = 0.0f
        if (cartList.size == 0){
            totalPrice = 0F
        }
        else{
            for (itemDetail in cartList){
                totalPrice += (itemDetail.price!! * itemDetail.itemAmount)
            }
        }

        return totalPrice
    }
}