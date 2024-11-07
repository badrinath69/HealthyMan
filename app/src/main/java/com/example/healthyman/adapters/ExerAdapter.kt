package com.example.healthyman.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.healthyman.R
import com.example.stress.modal.Exer

class ExerAdapter(private val exer: List<Exer>, private val clickListener: (Exer) -> Unit) :
    RecyclerView.Adapter<ExerAdapter.ExerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ExerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerViewHolder, position: Int) {
        val exercise = exer[position]
        holder.bind(exercise,clickListener)
    }

    override fun getItemCount(): Int = exer.size

    class ExerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerNameTextView: TextView = itemView.findViewById(R.id.rv_name)
        val lay:ConstraintLayout = itemView.findViewById(R.id.cl_itemdummy)
        fun bind(list: Exer, clickListener: (Exer) -> Unit) {
            exerNameTextView.text = list.name
            lay.setOnClickListener { clickListener(list) }
        }
    }
}
