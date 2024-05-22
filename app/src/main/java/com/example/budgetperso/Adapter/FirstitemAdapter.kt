package com.example.budgetperso.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetperso.R

class FirstItemAdapter(private val data: List<Item>) : RecyclerView.Adapter<FirstItemAdapter.FirstItemViewHolder>() {

    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_home, parent, false)
        return FirstItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FirstItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FirstItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    interface ItemClickListener {
        fun onItemClick(item: Item)
    }

    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }
}
