package com.example.budgetperso.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetperso.R

class SecondItemAdapter(private val data: List<Item>) : RecyclerView.Adapter<SecondItemAdapter.SecondItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_home2, parent, false)
        return SecondItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class SecondItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val nameTextView: TextView = itemView.findViewById(R.id.name)

        fun bind(item: Item) {
            imageView.setImageResource(item.imageResId)
            titleTextView.text = item.title
            nameTextView.text = item.name
        }
    }

    data class Item(val id: Int, val title: String, val imageResId: Int, val name: String)
}
