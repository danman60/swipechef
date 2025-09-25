package com.swipechef.app.data.models

data class GroceryItem(
    val name: String,
    val quantity: String,
    val category: String,
    val completed: Boolean = false
) {
    override fun toString(): String {
        return if (quantity.isNotBlank()) {
            "$quantity $name"
        } else {
            name
        }
    }

    fun toggleCompleted(): GroceryItem {
        return copy(completed = !completed)
    }

    companion object {
        fun categorizeIngredient(ingredientName: String): String {
            val name = ingredientName.lowercase()
            return when {
                // Produce
                name.contains(Regex("(onion|garlic|tomato|potato|carrot|celery|pepper|lettuce|spinach|apple|banana|lemon|lime|orange|herbs|basil|parsley|cilantro|scallion|ginger|mushroom|broccoli|cauliflower|cucumber|zucchini|avocado)")) -> "Produce"

                // Meat & Protein
                name.contains(Regex("(chicken|beef|pork|fish|salmon|shrimp|turkey|bacon|ham|sausage|ground|steak|chop|thigh|breast|egg|tofu)")) -> "Meat"

                // Dairy
                name.contains(Regex("(milk|cheese|butter|cream|yogurt|sour cream|mozzarella|cheddar|parmesan|ricotta)")) -> "Dairy"

                // Bakery
                name.contains(Regex("(bread|bun|roll|tortilla|pita|bagel|croissant|muffin|cake|cookie)")) -> "Bakery"

                // Frozen
                name.contains(Regex("(frozen|ice cream|peas|corn|berry|pizza|ice)")) -> "Frozen"

                // Pantry staples
                name.contains(Regex("(flour|sugar|salt|pepper|oil|vinegar|sauce|pasta|rice|bean|lentil|quinoa|oat|cereal|spice|seasoning|stock|broth|can|jar|bottle|honey|vanilla|baking)")) -> "Pantry"

                else -> "Other"
            }
        }
    }
}