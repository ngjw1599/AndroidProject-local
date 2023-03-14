package com.example.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class foodAdapter(private val foodList: ArrayList<FoodItemClass>,
    private val listener: OnItemClickListener):
    RecyclerView.Adapter<foodAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val foodtextView = itemView.findViewById<TextView>(R.id.foodName)
        val foodPhoto = itemView.findViewById<ImageView>(R.id.foodImage)

        // cart button
        val foodCart = itemView.findViewById<Button>(R.id.cart_button)

        init{
            foodCart.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            val image = foodList[position].photo
            val name = foodList[position].name
            val desc = foodList[position].desc
            val price = foodList[position].price
            if (position != RecyclerView.NO_POSITION){
                listener.passData(position,image,name,desc,price)
            }

        }

    }
    interface OnItemClickListener{
        fun passData(position: Int, image: Int, name: String, desc: String, price: Float)
    }

    //https://guides.codepath.com/android/using-the-recyclerview

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): foodAdapter.MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.menucard_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        // returns the size of the array for recyclerview to determine number of items needed
        return foodList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.foodPhoto.setImageResource(currentItem.photo)
        holder.foodtextView.text = currentItem.name
    }




}