package com.swipechef.app.ui.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.swipechef.app.data.SampleRecipes
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.FragmentPlanMealsBinding
import com.swipechef.app.ui.shared.SharedRecipeViewModel

class PlanMealsFragment : Fragment() {

    private var _binding: FragmentPlanMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesAdapter: RecipesSwipeAdapter
    private val allRecipes = SampleRecipes.getSampleRecipes()
    private var currentRecipeIndex = 0

    // Shared ViewModel for chosen recipes
    private val sharedViewModel: SharedRecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecipeCards()
        observeViewModel()
        updateCounter()
    }

    private fun observeViewModel() {
        sharedViewModel.showAddedToast.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                sharedViewModel.clearToastMessage()
                updateCounter()
            }
        }

        sharedViewModel.chosenRecipes.observe(viewLifecycleOwner) {
            updateCounter()
        }
    }

    private fun addRecipeToChosen(recipe: Recipe) {
        sharedViewModel.addRecipeToChosen(recipe)
    }

    private fun setupRecipeCards() {
        recipesAdapter = RecipesSwipeAdapter(
            recipes = allRecipes,
            onRecipeClick = { recipe ->
                // Handle recipe tap for detailed view
                showRecipeDetails(recipe)
            },
            onRecipeSwipeUp = { recipe ->
                addRecipeToChosen(recipe)
            }
        )

        binding.recipeViewPager.adapter = recipesAdapter
        binding.recipeViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Handle swipe gestures
        binding.recipeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentRecipeIndex = position
                updateProgressIndicator()
            }
        })

        // Swipe action buttons
        binding.btnSkip.setOnClickListener {
            skipCurrentRecipe()
        }

        binding.btnChoose.setOnClickListener {
            // Find the currently visible recipe card and animate it
            val viewPager = binding.recipeViewPager
            val currentFragment = recipesAdapter.getCurrentRecipeCard(viewPager)
            currentFragment?.animateChooseButton()
            chooseCurrentRecipe()
        }

        updateProgressIndicator()
    }

    private fun skipCurrentRecipe() {
        moveToNextRecipe()
    }

    private fun chooseCurrentRecipe() {
        val currentRecipe = allRecipes[currentRecipeIndex]
        addRecipeToChosen(currentRecipe)
        moveToNextRecipe()
    }

    private fun moveToNextRecipe() {
        if (currentRecipeIndex < allRecipes.size - 1) {
            binding.recipeViewPager.setCurrentItem(currentRecipeIndex + 1, true)
        } else {
            // Show completion message or loop back to start
            binding.recipeViewPager.setCurrentItem(0, true)
        }
    }


    private fun showRecipeDetails(recipe: Recipe) {
        Toast.makeText(requireContext(), "Recipe details for: ${recipe.title}", Toast.LENGTH_SHORT).show()
        // TODO: Navigate to recipe detail fragment
    }

    private fun updateCounter() {
        val chosenCount = sharedViewModel.getChosenRecipesCount()
        binding.chosenCount.text = "$chosenCount recipes chosen"
        binding.btnViewChosen.visibility = if (chosenCount == 0) View.GONE else View.VISIBLE

        binding.btnViewChosen.setOnClickListener {
            // Navigate to chosen meals tab
            // TODO: Implement navigation to chosen meals fragment
        }
    }

    private fun updateProgressIndicator() {
        val progress = ((currentRecipeIndex + 1).toFloat() / allRecipes.size) * 100
        binding.progressText.text = "${currentRecipeIndex + 1} of ${allRecipes.size}"
        binding.progressBar.progress = progress.toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PlanMealsFragment()
    }
}