package com.swipechef.app.data.models

data class PlanningSession(
    val selectedRecipes: MutableList<Recipe> = mutableListOf(),
    val targetCount: Int = 7,
    val currentIndex: Int = 0
) {
    fun addRecipe(recipe: Recipe): Boolean {
        if (selectedRecipes.size < targetCount && !selectedRecipes.any { it.id == recipe.id }) {
            selectedRecipes.add(recipe)
            return true
        }
        return false
    }

    fun removeRecipe(recipeId: String): Boolean {
        return selectedRecipes.removeAll { it.id == recipeId }
    }

    fun isComplete(): Boolean {
        return selectedRecipes.size >= targetCount
    }

    fun getProgress(): Float {
        return selectedRecipes.size.toFloat() / targetCount.toFloat()
    }

    fun canAddMore(): Boolean {
        return selectedRecipes.size < targetCount
    }

    fun getRemainingSlots(): Int {
        return (targetCount - selectedRecipes.size).coerceAtLeast(0)
    }

    fun clear() {
        selectedRecipes.clear()
    }
}