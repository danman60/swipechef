package com.swipechef.app.ui.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swipechef.app.data.models.GroceryItem
import com.swipechef.app.data.models.Ingredient
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.FragmentGroceryListBinding
import com.swipechef.app.utils.GroceryListGenerator

class GroceryListFragment : Fragment() {

    private var _binding: FragmentGroceryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var groceryAdapter: GroceryListAdapter
    private val groceryItems = mutableListOf<GroceryItem>()
    private val chosenRecipes = mutableListOf<Recipe>() // This would come from shared ViewModel in real app

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroceryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        generateGroceryList()
        updateEmptyState()
    }

    private fun setupRecyclerView() {
        groceryAdapter = GroceryListAdapter(groceryItems) { item ->
            toggleItemCompleted(item)
        }

        binding.recyclerViewGrocery.apply {
            adapter = groceryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun generateGroceryList() {
        if (chosenRecipes.isEmpty()) return

        val allIngredients = chosenRecipes.flatMap { it.ingredients }
        val generatedList = GroceryListGenerator.generateGroceryList(
            "Weekly Meal Plan",
            allIngredients
        )

        groceryItems.clear()
        groceryItems.addAll(generatedList.items)
        groceryAdapter.notifyDataSetChanged()

        updateStats()
    }

    private fun toggleItemCompleted(item: GroceryItem) {
        val position = groceryItems.indexOf(item)
        if (position >= 0) {
            groceryItems[position] = item.copy(isCompleted = !item.isCompleted)
            groceryAdapter.notifyItemChanged(position)
            updateStats()
        }
    }

    private fun updateEmptyState() {
        if (groceryItems.isEmpty()) {
            binding.recyclerViewGrocery.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.statsLayout.visibility = View.GONE
        } else {
            binding.recyclerViewGrocery.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
            binding.statsLayout.visibility = View.VISIBLE
        }
    }

    private fun updateStats() {
        val completedItems = groceryItems.count { it.isCompleted }
        val totalItems = groceryItems.size

        binding.completedCount.text = "$completedItems of $totalItems completed"

        if (totalItems > 0) {
            val progressPercent = (completedItems.toFloat() / totalItems * 100).toInt()
            binding.progressBar.progress = progressPercent
        }
    }

    fun updateChosenRecipes(recipes: List<Recipe>) {
        chosenRecipes.clear()
        chosenRecipes.addAll(recipes)
        generateGroceryList()
        updateEmptyState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = GroceryListFragment()
    }
}