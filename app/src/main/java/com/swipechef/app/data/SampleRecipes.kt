package com.swipechef.app.data

import com.swipechef.app.data.models.Recipe
import com.swipechef.app.data.models.Ingredient

object SampleRecipes {
    fun getSampleRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = "1",
                title = "Spaghetti Carbonara",
                description = "Classic Italian pasta dish with eggs, cheese, and pancetta. Cook time: 20 min • Medium difficulty",
                imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400",
                cookTime = 20,
                servings = 4,
                steps = listOf(
                    "Cook spaghetti according to package directions",
                    "Fry pancetta until crispy",
                    "Whisk eggs and parmesan together",
                    "Toss hot pasta with pancetta and egg mixture",
                    "Season with black pepper and serve"
                ),
                ingredients = listOf(
                    Ingredient(item = "Spaghetti", quantity = "1 lb", category = "Pasta"),
                    Ingredient(item = "Pancetta", quantity = "6 oz", category = "Meat"),
                    Ingredient(item = "Eggs", quantity = "4 large", category = "Dairy"),
                    Ingredient(item = "Parmesan cheese", quantity = "1 cup grated", category = "Dairy"),
                    Ingredient(item = "Black pepper", quantity = "to taste", category = "Spices")
                ),
                tags = listOf("Italian", "Pasta", "Quick"),
                userId = "sample_user"
            ),
            Recipe(
                id = "2",
                title = "Chicken Tikka Masala",
                description = "Creamy tomato-based curry with tender chicken pieces. Cook time: 45 min • Medium difficulty",
                imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400",
                cookTime = 45,
                servings = 6,
                steps = listOf(
                    "Marinate chicken in yogurt and spices",
                    "Grill chicken until cooked through",
                    "Make sauce with tomatoes, cream, and spices",
                    "Combine chicken with sauce",
                    "Simmer and serve with rice"
                ),
                ingredients = listOf(
                    Ingredient(item = "Chicken breast", quantity = "2 lbs", category = "Meat"),
                    Ingredient(item = "Greek yogurt", quantity = "1 cup", category = "Dairy"),
                    Ingredient(item = "Canned tomatoes", quantity = "1 can", category = "Pantry"),
                    Ingredient(item = "Heavy cream", quantity = "1/2 cup", category = "Dairy"),
                    Ingredient(item = "Garam masala", quantity = "2 tsp", category = "Spices"),
                    Ingredient(item = "Basmati rice", quantity = "2 cups", category = "Grains")
                ),
                tags = listOf("Indian", "Curry", "Chicken"),
                userId = "sample_user"
            ),
            Recipe(
                id = "3",
                title = "Caesar Salad",
                description = "Fresh romaine lettuce with creamy caesar dressing and croutons. Cook time: 15 min • Easy difficulty",
                imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400",
                cookTime = 15,
                servings = 4,
                steps = listOf(
                    "Wash and chop romaine lettuce",
                    "Make dressing with mayo, parmesan, and lemon",
                    "Toss lettuce with dressing",
                    "Top with croutons and extra parmesan"
                ),
                ingredients = listOf(
                    Ingredient(item = "Romaine lettuce", quantity = "2 heads", category = "Produce"),
                    Ingredient(item = "Parmesan cheese", quantity = "1/2 cup", category = "Dairy"),
                    Ingredient(item = "Mayonnaise", quantity = "1/4 cup", category = "Condiments"),
                    Ingredient(item = "Lemon", quantity = "1 whole", category = "Produce"),
                    Ingredient(item = "Croutons", quantity = "1 cup", category = "Bakery")
                ),
                tags = listOf("Salad", "Vegetarian", "Quick"),
                userId = "sample_user"
            ),
            Recipe(
                id = "4",
                title = "Beef Tacos",
                description = "Seasoned ground beef with fresh toppings in soft tortillas. Cook time: 25 min • Easy difficulty",
                imageUrl = "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=400",
                cookTime = 25,
                servings = 4,
                steps = listOf(
                    "Brown ground beef with taco seasoning",
                    "Warm tortillas",
                    "Fill tortillas with beef",
                    "Add fresh toppings like lettuce, tomatoes, cheese"
                ),
                ingredients = listOf(
                    Ingredient(item = "Ground beef", quantity = "1 lb", category = "Meat"),
                    Ingredient(item = "Taco seasoning", quantity = "1 packet", category = "Spices"),
                    Ingredient(item = "Soft tortillas", quantity = "8 count", category = "Bakery"),
                    Ingredient(item = "Lettuce", quantity = "1 head", category = "Produce"),
                    Ingredient(item = "Tomatoes", quantity = "2 medium", category = "Produce"),
                    Ingredient(item = "Cheddar cheese", quantity = "2 cups shredded", category = "Dairy")
                ),
                tags = listOf("Mexican", "Beef", "Family-friendly"),
                userId = "sample_user"
            ),
            Recipe(
                id = "5",
                title = "Chocolate Chip Cookies",
                description = "Classic soft and chewy chocolate chip cookies. Cook time: 30 min • Easy difficulty",
                imageUrl = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400",
                cookTime = 30,
                servings = 24,
                steps = listOf(
                    "Cream butter and sugars",
                    "Add eggs and vanilla",
                    "Mix in flour and baking soda",
                    "Fold in chocolate chips",
                    "Bake at 375°F for 9-11 minutes"
                ),
                ingredients = listOf(
                    Ingredient(item = "Butter", quantity = "1 cup", category = "Dairy"),
                    Ingredient(item = "Brown sugar", quantity = "1 cup", category = "Pantry"),
                    Ingredient(item = "White sugar", quantity = "1/2 cup", category = "Pantry"),
                    Ingredient(item = "Eggs", quantity = "2 large", category = "Dairy"),
                    Ingredient(item = "All-purpose flour", quantity = "2 1/4 cups", category = "Pantry"),
                    Ingredient(item = "Chocolate chips", quantity = "2 cups", category = "Pantry")
                ),
                tags = listOf("Dessert", "Cookies", "Baking"),
                userId = "sample_user"
            ),
            Recipe(
                id = "6",
                title = "Greek Salad",
                description = "Fresh Mediterranean salad with feta cheese and olives. Cook time: 10 min • Easy difficulty",
                imageUrl = "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400",
                cookTime = 10,
                servings = 4,
                steps = listOf(
                    "Chop cucumbers, tomatoes, and onion",
                    "Add olives and feta cheese",
                    "Drizzle with olive oil and lemon juice",
                    "Season with oregano and salt"
                ),
                ingredients = listOf(
                    Ingredient(item = "Cucumbers", quantity = "2 large", category = "Produce"),
                    Ingredient(item = "Tomatoes", quantity = "3 medium", category = "Produce"),
                    Ingredient(item = "Red onion", quantity = "1/2 medium", category = "Produce"),
                    Ingredient(item = "Feta cheese", quantity = "4 oz", category = "Dairy"),
                    Ingredient(item = "Kalamata olives", quantity = "1/2 cup", category = "Pantry"),
                    Ingredient(item = "Olive oil", quantity = "3 tbsp", category = "Pantry")
                ),
                tags = listOf("Greek", "Vegetarian", "Healthy"),
                userId = "sample_user"
            )
        )
    }

    // Helper function to extract difficulty from description
    fun getDifficulty(recipe: Recipe): String {
        return when {
            recipe.description.contains("Easy difficulty") -> "Easy"
            recipe.description.contains("Hard difficulty") -> "Hard"
            else -> "Medium"
        }
    }
}