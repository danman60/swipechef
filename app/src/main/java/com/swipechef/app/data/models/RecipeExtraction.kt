package com.swipechef.app.data.models

data class RecipeExtraction(
    val title: String,
    val summary: String? = null,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val cookTime: Int? = null,
    val prepTime: Int? = null,
    val servings: Int? = null,
    val mealType: String? = null,
    val tags: List<String> = emptyList(),
    val validation: ValidationResult? = null
) {
    fun toRecipe(userId: String, imageUrl: String? = null, sourceUrl: String? = null): Recipe {
        val processedIngredients = ingredients.map { ingredientText ->
            val parts = ingredientText.split(" ", limit = 3)
            val quantity = if (parts.size > 1 && parts[0].matches(Regex("[0-9./\\-]+|[0-9]+[a-zA-Z]+"))) {
                parts[0] + if (parts.size > 2 && parts[1].length <= 3) " ${parts[1]}" else ""
            } else ""

            val item = if (quantity.isNotEmpty()) {
                ingredientText.removePrefix(quantity).trim()
            } else {
                ingredientText
            }

            Ingredient(
                item = item,
                quantity = quantity,
                category = GroceryItem.categorizeIngredient(item)
            )
        }

        return Recipe(
            title = title,
            description = summary ?: description,
            ingredients = processedIngredients,
            steps = steps,
            imageUrl = imageUrl,
            sourceLink = sourceUrl,
            cookTime = cookTime,
            prepTime = prepTime,
            servings = servings,
            mealType = mealType ?: "Any",
            tags = tags,
            userId = userId
        )
    }
}

data class ValidationResult(
    val missing: List<String> = emptyList(),
    val warnings: List<String> = emptyList()
) {
    fun isValid(): Boolean = missing.isEmpty()
}