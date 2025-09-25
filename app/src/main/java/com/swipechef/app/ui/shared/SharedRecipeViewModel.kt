package com.swipechef.app.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swipechef.app.data.models.Recipe

class SharedRecipeViewModel : ViewModel() {

    private val _chosenRecipes = MutableLiveData<MutableList<Recipe>>(mutableListOf())
    val chosenRecipes: LiveData<MutableList<Recipe>> = _chosenRecipes

    private val _lastAddedRecipe = MutableLiveData<Recipe?>()
    val lastAddedRecipe: LiveData<Recipe?> = _lastAddedRecipe

    private val _showAddedToast = MutableLiveData<String?>()
    val showAddedToast: LiveData<String?> = _showAddedToast

    fun addRecipeToChosen(recipe: Recipe) {
        val currentList = _chosenRecipes.value ?: mutableListOf()

        // Check if already chosen (idempotent)
        if (currentList.any { it.id == recipe.id }) {
            _showAddedToast.value = "${recipe.title} is already in your chosen meals"
            return
        }

        // Add to chosen list
        currentList.add(recipe)
        _chosenRecipes.value = currentList
        _lastAddedRecipe.value = recipe
        _showAddedToast.value = "Added ${recipe.title} to Chosen Meals"
    }

    fun removeRecipeFromChosen(recipe: Recipe) {
        val currentList = _chosenRecipes.value ?: mutableListOf()
        currentList.removeAll { it.id == recipe.id }
        _chosenRecipes.value = currentList
    }

    fun isRecipeChosen(recipe: Recipe): Boolean {
        return _chosenRecipes.value?.any { it.id == recipe.id } ?: false
    }

    fun clearToastMessage() {
        _showAddedToast.value = null
    }

    fun getChosenRecipesCount(): Int {
        return _chosenRecipes.value?.size ?: 0
    }
}