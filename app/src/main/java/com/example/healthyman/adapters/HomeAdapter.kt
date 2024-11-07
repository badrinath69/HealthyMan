package com.example.healthyman.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthyman.R
import com.example.stress.modal.HomeIssueList

class HomeAdapter(private var homelist: List<HomeIssueList>, private val clickListener: (HomeIssueList) -> Unit) :
RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item2, parent, false)
        return HomeViewHolder(view)
    }

    fun updateList(newList: List<HomeIssueList>) {
        homelist = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val list = homelist[position]
        holder.bind(list, clickListener)
    }

    override fun getItemCount(): Int = homelist.size

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.rv_name2)
        private val img: ImageView = itemView.findViewById(R.id.rv_image)

        fun bind(list: HomeIssueList, clickListener: (HomeIssueList) -> Unit) {
            itemName.text = list.name
            Glide.with(itemView.context)
                .load(list.imageurl)
                .placeholder(R.drawable.logo)
                .into(img)
            itemView.setOnClickListener { clickListener(list) }
        }
    }
}
