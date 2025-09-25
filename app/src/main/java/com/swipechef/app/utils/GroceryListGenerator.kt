package com.swipechef.app.utils

import com.swipechef.app.data.models.GroceryItem
import com.swipechef.app.data.models.GroceryList
import com.swipechef.app.data.models.Recipe
import java.util.UUID

class GroceryListGenerator {

    fun generateList(
        recipes: List<Recipe>,
        pantry: List<String> = emptyList(),
        userId: String
    ): GroceryList {
        // Combine all ingredients from selected recipes
        val allIngredients = recipes.flatMap { recipe ->
            recipe.ingredients.map { ingredient ->
                ingredient.toGroceryItem()
            }
        }

        // Merge duplicate items
        val mergedItems = mergeDuplicateIngredients(allIngredients)

        // Remove pantry items
        val filteredItems = mergedItems.filter { item ->
            !pantry.any { pantryItem ->
                item.name.lowercase().contains(pantryItem.lowercase()) ||
                pantryItem.lowercase().contains(item.name.lowercase())
            }
        }

        // Sort items by category and then alphabetically
        val sortedItems = sortItemsByCategory(filteredItems)

        return GroceryList(
            userId = userId,
            recipeIds = recipes.map { it.id },
            items = sortedItems,
            name = generateListName(recipes)
        )
    }

    private fun mergeDuplicateIngredients(items: List<GroceryItem>): List<GroceryItem> {
        val groupedItems = items.groupBy { normalizeItemName(it.name) }

        return groupedItems.map { (normalizedName, itemGroup) ->
            if (itemGroup.size == 1) {
                itemGroup.first()
            } else {
                // Merge quantities when possible
                val quantities = itemGroup.mapNotNull { parseQuantity(it.quantity) }
                val mergedQuantity = if (quantities.isNotEmpty()) {
                    val total = quantities.sum()
                    val unit = itemGroup.first().quantity.replace(Regex("[0-9./\\-\\s]+"), "").trim()
                    if (unit.isNotEmpty()) "$total $unit" else total.toString()
                } else {
                    itemGroup.first().quantity
                }

                GroceryItem(
                    name = itemGroup.first().name,
                    quantity = mergedQuantity,
                    category = itemGroup.first().category,
                    completed = false
                )
            }
        }
    }

    private fun normalizeItemName(name: String): String {
        return name.lowercase()
            .replace(Regex("\\b(fresh|frozen|dried|chopped|diced|sliced|ground|whole|large|small|medium)\\b"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
            .let { normalized ->
                // Handle synonyms
                when {
                    normalized.contains("scallion") -> normalized.replace("scallion", "green onion")
                    normalized.contains("cilantro") -> normalized.replace("cilantro", "coriander")
                    normalized.contains("bell pepper") -> normalized.replace("bell pepper", "pepper")
                    else -> normalized
                }
            }
    }

    private fun parseQuantity(quantity: String): Double? {
        if (quantity.isBlank()) return null

        // Extract numeric part (handles fractions like 1/2, 1-1/2, etc.)
        val numberMatch = Regex("([0-9]+(?:[./][0-9]+)?(?:-[0-9]+(?:[./][0-9]+)?)?)").find(quantity)
        val numberStr = numberMatch?.value ?: return null

        return try {
            when {
                numberStr.contains("-") -> {
                    // Handle ranges like "1-2" -> take average
                    val parts = numberStr.split("-")
                    (parts[0].toDouble() + parts[1].toDouble()) / 2
                }
                numberStr.contains("/") -> {
                    // Handle fractions like "1/2"
                    val parts = numberStr.split("/")
                    parts[0].toDouble() / parts[1].toDouble()
                }
                else -> numberStr.toDouble()
            }
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun sortItemsByCategory(items: List<GroceryItem>): List<GroceryItem> {
        val categoryOrder = listOf("Produce", "Meat", "Dairy", "Bakery", "Pantry", "Frozen", "Other")

        return items.sortedWith(compareBy<GroceryItem> { item ->
            categoryOrder.indexOf(item.category).takeIf { it >= 0 } ?: categoryOrder.size
        }.thenBy { it.name.lowercase() })
    }

    private fun generateListName(recipes: List<Recipe>): String {
        val dateFormat = java.text.SimpleDateFormat("MM/dd", java.util.Locale.getDefault())
        val currentDate = dateFormat.format(java.util.Date())

        return when (recipes.size) {
            0 -> "Grocery List $currentDate"
            1 -> "List for ${recipes.first().title} - $currentDate"
            else -> "List for ${recipes.size} recipes - $currentDate"
        }
    }
}