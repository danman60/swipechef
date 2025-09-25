package com.swipechef.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDeckViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _filteredRecipes = MutableLiveData<List<Recipe>>()
    val filteredRecipes: LiveData<List<Recipe>> = _filteredRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var currentQuery = ""
    private var allRecipes: List<Recipe> = emptyList()

    fun loadRecipes(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAllRecipes(userId).fold(
                onSuccess = { recipes ->
                    allRecipes = recipes
                    _recipes.value = recipes
                    applyFilter(currentQuery)
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                }
            )
        }
    }

    fun searchRecipes(query: String) {
        currentQuery = query
        applyFilter(query)
    }

    private fun applyFilter(query: String) {
        val filtered = if (query.isBlank()) {
            allRecipes
        } else {
            allRecipes.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) ||
                recipe.description.contains(query, ignoreCase = true) ||
                recipe.tags.any { it.contains(query, ignoreCase = true) } ||
                recipe.ingredients.any { it.item.contains(query, ignoreCase = true) }
            }
        }
        _filteredRecipes.value = filtered
    }

    fun deleteRecipe(recipeId: String) {
        viewModelScope.launch {
            repository.deleteRecipe(recipeId).fold(
                onSuccess = {
                    allRecipes = allRecipes.filter { it.id != recipeId }
                    _recipes.value = allRecipes
                    applyFilter(currentQuery)
                },
                onFailure = { exception ->
                    _error.value = exception.message
                }
            )
        }
    }

    fun clearError() {
        _error.value = null
    }
}