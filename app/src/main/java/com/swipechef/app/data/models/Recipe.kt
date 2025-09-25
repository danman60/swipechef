package com.swipechef.app.data.models

import java.util.UUID

data class Recipe(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val imageUrl: String? = null,
    val sourceLink: String? = null,
    val cookTime: Int? = null,
    val prepTime: Int? = null,
    val servings: Int? = null,
    val mealType: String = "Any",
    val tags: List<String> = emptyList(),
    val ratings: RecipeRatings? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String
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