package com.swipechef.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swipechef.app.data.models.GroceryList
import com.swipechef.app.data.models.PlanningSession
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.data.repository.RecipeRepository
import com.swipechef.app.utils.GroceryListGenerator
import kotlinx.coroutines.launch

class SwipePlanningViewModel : ViewModel() {

    private val repository = RecipeRepository()
    private val groceryListGenerator = GroceryListGenerator()

    private val _session = MutableLiveData(PlanningSession())
    val session: LiveData<PlanningSession> = _session

    private val _availableRecipes = MutableLiveData<List<Recipe>>()
    val availableRecipes: LiveData<List<Recipe>> = _availableRecipes

    private val _currentRecipeIndex = MutableLiveData(0)
    val currentRecipeIndex: LiveData<Int> = _currentRecipeIndex

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadRecipes(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAllRecipes(userId).fold(
                onSuccess = { recipes ->
                    _availableRecipes.value = recipes
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                }
            )
        }
    }

    fun addRecipeToSession(recipe: Recipe) {
        val currentSession = _session.value ?: return
        if (currentSession.addRecipe(recipe)) {
            _session.value = currentSession
        }
    }

    fun removeRecipeFromSession(recipeId: String) {
        val currentSession = _session.value ?: return
        if (currentSession.removeRecipe(recipeId)) {
            _session.value = currentSession
        }
    }

    fun nextRecipe() {
        val currentIndex = _currentRecipeIndex.value ?: 0
        val availableCount = _availableRecipes.value?.size ?: 0
        if (currentIndex < availableCount - 1) {
            _currentRecipeIndex.value = currentIndex + 1
        }
    }

    fun previousRecipe() {
        val currentIndex = _currentRecipeIndex.value ?: 0
        if (currentIndex > 0) {
            _currentRecipeIndex.value = currentIndex - 1
        }
    }

    fun resetCurrentIndex() {
        _currentRecipeIndex.value = 0
    }

    fun generateGroceryList(userId: String): GroceryList? {
        val currentSession = _session.value
        return if (currentSession != null && currentSession.selectedRecipes.isNotEmpty()) {
            groceryListGenerator.generateList(
                recipes = currentSession.selectedRecipes,
                userId = userId
            )
        } else {
            null
        }
    }

    fun saveGroceryList(groceryList: GroceryList, callback: (Result<GroceryList>) -> Unit) {
        viewModelScope.launch {
            val result = repository.saveGroceryList(groceryList)
            callback(result)
        }
    }

    fun resetSession() {
        _session.value = PlanningSession()
        _currentRecipeIndex.value = 0
    }

    fun clearError() {
        _error.value = null
    }
}