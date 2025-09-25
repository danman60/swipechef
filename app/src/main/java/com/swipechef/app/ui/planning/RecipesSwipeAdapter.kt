package com.swipechef.app.ui.planning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swipechef.app.data.SampleRecipes
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.RecipeCardSimpleBinding

class RecipesSwipeAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipesSwipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(private val binding: RecipeCardSimpleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.title
            binding.recipeDescription.text = recipe.description
            binding.cookTime.text = "${recipe.cookTime} min"
            binding.servings.text = "${recipe.servings} servings"
            binding.difficulty.text = SampleRecipes.getDifficulty(recipe)

            // TODO: Load image from URL using Glide or similar
            // For now, we'll use a placeholder
            // Glide.with(binding.recipeImage.context)
            //     .load(recipe.imageUrl)
            //     .placeholder(R.drawable.placeholder_recipe)
            //     .into(binding.recipeImage)

            binding.root.setOnClickListener {
                onRecipeClick(recipe)
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
}