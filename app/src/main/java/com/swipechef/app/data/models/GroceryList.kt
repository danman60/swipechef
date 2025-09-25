package com.swipechef.app.data.models

import java.util.UUID

data class GroceryList(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val recipeIds: List<String>,
    val items: List<GroceryItem>,
    val createdAt: Long = System.currentTimeMillis(),
    val name: String = "Grocery List ${java.text.SimpleDateFormat("MM/dd", java.util.Locale.getDefault()).format(java.util.Date(createdAt))}"
) {
    fun getItemsByCategory(): Map<String, List<GroceryItem>> {
        val categoryOrder = listOf("Produce", "Meat", "Dairy", "Bakery", "Pantry", "Frozen", "Other")
        return items.groupBy { it.category }
            .toSortedMap { a, b ->
                val aIndex = categoryOrder.indexOf(a).takeIf { it >= 0 } ?: categoryOrder.size
                val bIndex = categoryOrder.indexOf(b).takeIf { it >= 0 } ?: categoryOrder.size
                aIndex.compareTo(bIndex)
            }
    }

    fun getTotalItemCount(): Int = items.size

    fun getCompletedItemCount(): Int = items.count { it.completed }
}