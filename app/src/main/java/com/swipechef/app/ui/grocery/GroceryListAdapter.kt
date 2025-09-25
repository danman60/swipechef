package com.swipechef.app.ui.grocery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swipechef.app.data.models.GroceryItem
import com.swipechef.app.databinding.ItemGroceryBinding

class GroceryListAdapter(
    private val items: List<GroceryItem>,
    private val onItemClick: (GroceryItem) -> Unit
) : RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(private val binding: ItemGroceryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroceryItem) {
            binding.itemName.text = item.name
            binding.itemQuantity.text = item.quantity
            binding.itemCategory.text = item.category
            binding.checkbox.isChecked = item.completed

            // Apply strike-through for completed items
            binding.itemName.alpha = if (item.completed) 0.6f else 1.0f
            binding.itemQuantity.alpha = if (item.completed) 0.6f else 1.0f

            binding.checkbox.setOnClickListener {
                onItemClick(item)
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = ItemGroceryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GroceryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}