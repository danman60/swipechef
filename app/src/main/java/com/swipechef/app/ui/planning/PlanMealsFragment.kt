package com.swipechef.app.ui.planning

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.swipechef.app.R
import com.swipechef.app.data.SampleRecipes
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.FragmentPlanMealsBinding

class PlanMealsFragment : Fragment() {

    private var _binding: FragmentPlanMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesAdapter: RecipesSwipeAdapter
    private val allRecipes = SampleRecipes.getSampleRecipes()
    private val chosenRecipes = mutableListOf<Recipe>()
    private var currentRecipeIndex = 0

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
        updateCounter()
    }

    private fun setupRecipeCards() {
        recipesAdapter = RecipesSwipeAdapter(allRecipes) { recipe ->
            // Handle recipe tap for detailed view
            showRecipeDetails(recipe)
        }

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
            chooseCurrentRecipe()
        }

        updateProgressIndicator()
    }

    private fun skipCurrentRecipe() {
        animateSkip()
        moveToNextRecipe()
    }

    private fun chooseCurrentRecipe() {
        val currentRecipe = allRecipes[currentRecipeIndex]
        chosenRecipes.add(currentRecipe)

        animateChoose()
        updateCounter()
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

    private fun animateSkip() {
        val currentCard = binding.recipeViewPager.findViewById<View>(R.id.recipeCard)
        currentCard?.let {
            val animator = AnimatorInflater.loadAnimator(requireContext(), android.R.animator.slide_out_right) as AnimatorSet
            animator.setTarget(it)
            animator.start()
        }
    }

    private fun animateChoose() {
        val currentCard = binding.recipeViewPager.findViewById<View>(R.id.recipeCard)
        currentCard?.let {
            val animator = AnimatorInflater.loadAnimator(requireContext(), android.R.animator.fade_in) as AnimatorSet
            animator.setTarget(it)
            animator.start()
        }
    }

    private fun showRecipeDetails(recipe: Recipe) {
        // TODO: Navigate to recipe detail fragment
        // For now, just show a toast or dialog
    }

    private fun updateCounter() {
        binding.chosenCount.text = "${chosenRecipes.size} recipes chosen"
        binding.btnViewChosen.visibility = if (chosenRecipes.isEmpty()) View.GONE else View.VISIBLE

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