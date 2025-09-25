package com.swipechef.app.data.models

data class Ingredient(
    val item: String,
    val quantity: String,
    val category: String = "Other"
) {
    override fun toString(): String {
        return if (quantity.isNotBlank()) {
            "$quantity $item"
        } else {
            item
        }
    }

    fun toGroceryItem(): GroceryItem {
        return GroceryItem(
            name = item,
            quantity = quantity,
            category = category,
            completed = false
        )
    }
}