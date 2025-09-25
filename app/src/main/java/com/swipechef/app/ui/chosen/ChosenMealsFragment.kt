package com.swipechef.app.ui.chosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.FragmentChosenMealsBinding

class ChosenMealsFragment : Fragment() {

    private var _binding: FragmentChosenMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var chosenMealsAdapter: ChosenMealsAdapter
    private val chosenRecipes = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChosenMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateEmptyState()
    }

    private fun setupRecyclerView() {
        chosenMealsAdapter = ChosenMealsAdapter(
            chosenRecipes,
            onRecipeClick = { recipe -> showRecipeDetails(recipe) },
            onRemoveClick = { recipe -> removeRecipe(recipe) }
        )

        binding.recyclerViewChosen.apply {
            adapter = chosenMealsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showRecipeDetails(recipe: Recipe) {
        // TODO: Navigate to recipe detail view
    }

    private fun removeRecipe(recipe: Recipe) {
        val position = chosenRecipes.indexOf(recipe)
        if (position >= 0) {
            chosenRecipes.removeAt(position)
            chosenMealsAdapter.notifyItemRemoved(position)
            updateEmptyState()
            updateRecipeCount()
        }
    }

    private fun updateEmptyState() {
        if (chosenRecipes.isEmpty()) {
            binding.recyclerViewChosen.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.generateGroceryButton.visibility = View.GONE
        } else {
            binding.recyclerViewChosen.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
            binding.generateGroceryButton.visibility = View.VISIBLE
        }
    }

    private fun updateRecipeCount() {
        binding.recipeCount.text = "${chosenRecipes.size} recipes selected"
    }

    fun addChosenRecipe(recipe: Recipe) {
        if (!chosenRecipes.contains(recipe)) {
            chosenRecipes.add(recipe)
            chosenMealsAdapter.notifyItemInserted(chosenRecipes.size - 1)
            updateEmptyState()
            updateRecipeCount()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ChosenMealsFragment()
    }
}