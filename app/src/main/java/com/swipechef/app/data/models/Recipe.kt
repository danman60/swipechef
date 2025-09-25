package com.swipechef.app.data.models

import java.util.UUID

// Enhanced Recipe model per TRD requirements
data class Recipe(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val sourceUrl: String? = null,
    val imageUrl: String? = null,
    val imageSource: ImageSource = ImageSource.PROVIDED,
    val imageAttribution: String? = null,
    val servings: Int? = null,
    val totalTimeMins: Int? = null,
    val cookTime: Int? = null,
    val prepTime: Int? = null,
    val tags: List<String> = emptyList(),

    // Raw ingredient lines (as entered/imported)
    val ingredients: List<Ingredient>,
    val steps: List<String>,

    // Normalized ingredients for grocery list
    val parsedIngredients: List<ParsedIngredient>? = null,

    // Metadata
    val mealType: String = "Any",
    val ratings: RecipeRatings? = null,
    val createdAt: String = System.currentTimeMillis().toString(),
    val updatedAt: String = System.currentTimeMillis().toString(),
    val userId: String,

    // UI state
    val isChosen: Boolean = false
) {
    fun getTotalTime(): Int? {
        return if (cookTime != null && prepTime != null) {
            cookTime + prepTime
        } else if (cookTime != null) {
            cookTime
        } else if (prepTime != null) {
            prepTime
        } else {
            null
        }
    }

    fun formatTime(minutes: Int?): String {
        return when {
            minutes == null -> ""
            minutes < 60 -> "${minutes} min"
            minutes % 60 == 0 -> "${minutes / 60} hr"
            else -> "${minutes / 60} hr ${minutes % 60} min"
        }
    }
}

data class RecipeRatings(
    val average: Float,
    val count: Int
)