package com.swipechef.app.ui.chosen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swipechef.app.data.SampleRecipes
import com.swipechef.app.data.models.Recipe
import com.swipechef.app.databinding.ItemChosenRecipeBinding

class ChosenMealsAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit,
    private val onRemoveClick: (Recipe) -> Unit
) : RecyclerView.Adapter<ChosenMealsAdapter.ChosenMealViewHolder>() {

    inner class ChosenMealViewHolder(private val binding: ItemChosenRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.title
            binding.recipeDescription.text = recipe.description
            binding.cookTime.text = "${recipe.cookTime} min"
            binding.servings.text = "${recipe.servings} servings"
            binding.difficulty.text = SampleRecipes.getDifficulty(recipe)
            binding.ingredientCount.text = "${recipe.ingredients.size} ingredients"

            // TODO: Load image from URL
            // Glide.with(binding.recipeImage.context)
            //     .load(recipe.imageUrl)
            //     .placeholder(R.drawable.placeholder_recipe)
            //     .into(binding.recipeImage)

            binding.root.setOnClickListener {
                onRecipeClick(recipe)
            }

            binding.btnRemove.setOnClickListener {
                onRemoveClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenMealViewHolder {
        val binding = ItemChosenRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChosenMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChosenMealViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size
}