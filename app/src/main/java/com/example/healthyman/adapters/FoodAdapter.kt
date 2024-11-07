package com.example.healthyman.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyman.R
import com.example.stress.modal.Exer
import com.example.stress.modal.Food

class FoodAdapter(private val foods: List<Food>, private val clickListener: (Food) -> Unit) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food,clickListener)

    }

    override fun getItemCount(): Int = foods.size

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.rv_name)
        val lay: ConstraintLayout = itemView.findViewById(R.id.cl_itemdummy)
        fun bind(list: Food, clickListener: (Food) -> Unit) {
            foodNameTextView.text = list.name
            lay.setOnClickListener { clickListener(list) }
        }
    }
}
