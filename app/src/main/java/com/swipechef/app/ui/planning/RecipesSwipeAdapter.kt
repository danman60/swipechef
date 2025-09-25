package com.swipechef.app.ui.planning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swipechef.app.data.SampleRecipes
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.RecipeCardSimpleBinding

class RecipesSwipeAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit,
    private val onRecipeSwipeUp: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipesSwipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(private val binding: RecipeCardSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            // Populate Pioneer Needles header
            binding.cardHeader.headerTitle.text = recipe.title
            val difficulty = SampleRecipes.getDifficulty(recipe)
            val subtitle = "${recipe.tags.firstOrNull() ?: "Recipe"} • $difficulty • ${recipe.cookTime} min"
            binding.cardHeader.headerSubtitle.text = subtitle

            // Main content
            binding.recipeDescription.text = recipe.description
            binding.cookTime.text = "${recipe.cookTime} min"
            binding.servings.text = "${recipe.servings} servings"
            binding.difficulty.text = difficulty

            // TODO: Load image from URL using Glide or similar
            // For now, we'll use a placeholder
            // Glide.with(binding.recipeImage.context)
            //     .load(recipe.imageUrl)
            //     .placeholder(R.drawable.placeholder_recipe)
            //     .into(binding.recipeImage)

            // Set up swipe gestures on the SwipeableRecipeCard
            (binding.root as? com.swipechef.app.ui.views.SwipeableRecipeCard)?.apply {
                setOnTapListener {
                    onRecipeClick(recipe)
                }
                setOnSwipeUpListener {
                    onRecipeSwipeUp(recipe)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeCardSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun getCurrentRecipeCard(viewPager: androidx.viewpager2.widget.ViewPager2): com.swipechef.app.ui.views.SwipeableRecipeCard? {
        val recyclerView = viewPager.getChildAt(0) as? androidx.recyclerview.widget.RecyclerView
        val layoutManager = recyclerView?.layoutManager as? androidx.recyclerview.widget.LinearLayoutManager

        val currentPosition = viewPager.currentItem
        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(currentPosition)

        return (viewHolder as? RecipeViewHolder)?.itemView as? com.swipechef.app.ui.views.SwipeableRecipeCard
    }
}