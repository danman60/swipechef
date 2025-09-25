package com.swipechef.app.data

import com.swipechef.app.data.models.Recipe
import com.swipechef.app.data.models.Ingredient

object SampleRecipes {
    fun getSampleRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = "1",
                title = "Spaghetti Carbonara",
                description = "Classic Italian pasta dish with eggs, cheese, and pancetta",
                imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400",
                cookTime = 20,
                servings = 4,
                difficulty = "Medium",
                instructions = listOf(
                    "Cook spaghetti according to package directions",
                    "Fry pancetta until crispy",
                    "Whisk eggs and parmesan together",
                    "Toss hot pasta with pancetta and egg mixture",
                    "Season with black pepper and serve"
                ),
                ingredients = listOf(
                    Ingredient(id = "1", name = "Spaghetti", quantity = "1 lb", category = "Pasta"),
                    Ingredient(id = "2", name = "Pancetta", quantity = "6 oz", category = "Meat"),
                    Ingredient(id = "3", name = "Eggs", quantity = "4 large", category = "Dairy"),
                    Ingredient(id = "4", name = "Parmesan cheese", quantity = "1 cup grated", category = "Dairy"),
                    Ingredient(id = "5", name = "Black pepper", quantity = "to taste", category = "Spices")
                ),
                tags = listOf("Italian", "Pasta", "Quick")
            ),
            Recipe(
                id = "2",
                title = "Chicken Tikka Masala",
                description = "Creamy tomato-based curry with tender chicken pieces",
                imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400",
                cookTime = 45,
                servings = 6,
                difficulty = "Medium",
                instructions = listOf(
                    "Marinate chicken in yogurt and spices",
                    "Grill chicken until cooked through",
                    "Make sauce with tomatoes, cream, and spices",
                    "Combine chicken with sauce",
                    "Simmer and serve with rice"
                ),
                ingredients = listOf(
                    Ingredient(id = "6", name = "Chicken breast", quantity = "2 lbs", category = "Meat"),
                    Ingredient(id = "7", name = "Greek yogurt", quantity = "1 cup", category = "Dairy"),
                    Ingredient(id = "8", name = "Canned tomatoes", quantity = "1 can", category = "Pantry"),
                    Ingredient(id = "9", name = "Heavy cream", quantity = "1/2 cup", category = "Dairy"),
                    Ingredient(id = "10", name = "Garam masala", quantity = "2 tsp", category = "Spices"),
                    Ingredient(id = "11", name = "Basmati rice", quantity = "2 cups", category = "Grains")
                ),
                tags = listOf("Indian", "Curry", "Chicken")
            ),
            Recipe(
                id = "3",
                title = "Caesar Salad",
                description = "Fresh romaine lettuce with creamy caesar dressing and croutons",
                imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400",
                cookTime = 15,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Wash and chop romaine lettuce",
                    "Make dressing with mayo, parmesan, and lemon",
                    "Toss lettuce with dressing",
                    "Top with croutons and extra parmesan"
                ),
                ingredients = listOf(
                    Ingredient(id = "12", name = "Romaine lettuce", quantity = "2 heads", category = "Produce"),
                    Ingredient(id = "13", name = "Parmesan cheese", quantity = "1/2 cup", category = "Dairy"),
                    Ingredient(id = "14", name = "Mayonnaise", quantity = "1/4 cup", category = "Condiments"),
                    Ingredient(id = "15", name = "Lemon", quantity = "1 whole", category = "Produce"),
                    Ingredient(id = "16", name = "Croutons", quantity = "1 cup", category = "Bakery")
                ),
                tags = listOf("Salad", "Vegetarian", "Quick")
            ),
            Recipe(
                id = "4",
                title = "Beef Tacos",
                description = "Seasoned ground beef with fresh toppings in soft tortillas",
                imageUrl = "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?w=400",
                cookTime = 25,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Brown ground beef with taco seasoning",
                    "Warm tortillas",
                    "Fill tortillas with beef",
                    "Add fresh toppings like lettuce, tomatoes, cheese"
                ),
                ingredients = listOf(
                    Ingredient(id = "17", name = "Ground beef", quantity = "1 lb", category = "Meat"),
                    Ingredient(id = "18", name = "Taco seasoning", quantity = "1 packet", category = "Spices"),
                    Ingredient(id = "19", name = "Soft tortillas", quantity = "8 count", category = "Bakery"),
                    Ingredient(id = "20", name = "Lettuce", quantity = "1 head", category = "Produce"),
                    Ingredient(id = "21", name = "Tomatoes", quantity = "2 medium", category = "Produce"),
                    Ingredient(id = "22", name = "Cheddar cheese", quantity = "2 cups shredded", category = "Dairy")
                ),
                tags = listOf("Mexican", "Beef", "Family-friendly")
            ),
            Recipe(
                id = "5",
                title = "Chocolate Chip Cookies",
                description = "Classic soft and chewy chocolate chip cookies",
                imageUrl = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400",
                cookTime = 30,
                servings = 24,
                difficulty = "Easy",
                instructions = listOf(
                    "Cream butter and sugars",
                    "Add eggs and vanilla",
                    "Mix in flour and baking soda",
                    "Fold in chocolate chips",
                    "Bake at 375°F for 9-11 minutes"
                ),
                ingredients = listOf(
                    Ingredient(id = "23", name = "Butter", quantity = "1 cup", category = "Dairy"),
                    Ingredient(id = "24", name = "Brown sugar", quantity = "1 cup", category = "Pantry"),
                    Ingredient(id = "25", name = "White sugar", quantity = "1/2 cup", category = "Pantry"),
                    Ingredient(id = "26", name = "Eggs", quantity = "2 large", category = "Dairy"),
                    Ingredient(id = "27", name = "All-purpose flour", quantity = "2 1/4 cups", category = "Pantry"),
                    Ingredient(id = "28", name = "Chocolate chips", quantity = "2 cups", category = "Pantry")
                ),
                tags = listOf("Dessert", "Cookies", "Baking")
            ),
            Recipe(
                id = "6",
                title = "Greek Salad",
                description = "Fresh Mediterranean salad with feta cheese and olives",
                imageUrl = "https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400",
                cookTime = 10,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Chop cucumbers, tomatoes, and onion",
                    "Add olives and feta cheese",
                    "Drizzle with olive oil and lemon juice",
                    "Season with oregano and salt"
                ),
                ingredients = listOf(
                    Ingredient(id = "29", name = "Cucumbers", quantity = "2 large", category = "Produce"),
                    Ingredient(id = "30", name = "Tomatoes", quantity = "3 medium", category = "Produce"),
                    Ingredient(id = "31", name = "Red onion", quantity = "1/2 medium", category = "Produce"),
                    Ingredient(id = "32", name = "Feta cheese", quantity = "4 oz", category = "Dairy"),
                    Ingredient(id = "33", name = "Kalamata olives", quantity = "1/2 cup", category = "Pantry"),
                    Ingredient(id = "34", name = "Olive oil", quantity = "3 tbsp", category = "Pantry")
                ),
                tags = listOf("Greek", "Vegetarian", "Healthy")
            ),
            Recipe(
                id = "7",
                title = "Salmon Teriyaki",
                description = "Glazed salmon fillets with sweet teriyaki sauce",
                imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400",
                cookTime = 20,
                servings = 4,
                difficulty = "Medium",
                instructions = listOf(
                    "Season salmon fillets",
                    "Pan-sear salmon skin side down",
                    "Flip and glaze with teriyaki sauce",
                    "Cook until sauce thickens",
                    "Serve with rice and vegetables"
                ),
                ingredients = listOf(
                    Ingredient(id = "35", name = "Salmon fillets", quantity = "4 pieces", category = "Seafood"),
                    Ingredient(id = "36", name = "Teriyaki sauce", quantity = "1/4 cup", category = "Condiments"),
                    Ingredient(id = "37", name = "Sesame oil", quantity = "1 tbsp", category = "Pantry"),
                    Ingredient(id = "38", name = "Green onions", quantity = "2 stalks", category = "Produce"),
                    Ingredient(id = "39", name = "Sesame seeds", quantity = "1 tbsp", category = "Pantry")
                ),
                tags = listOf("Japanese", "Seafood", "Healthy")
            ),
            Recipe(
                id = "8",
                title = "Vegetable Stir Fry",
                description = "Colorful mix of fresh vegetables in a savory sauce",
                imageUrl = "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=400",
                cookTime = 15,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Heat oil in wok or large pan",
                    "Add harder vegetables first",
                    "Stir-fry until tender-crisp",
                    "Add sauce and toss to coat",
                    "Serve over rice or noodles"
                ),
                ingredients = listOf(
                    Ingredient(id = "40", name = "Bell peppers", quantity = "2 mixed", category = "Produce"),
                    Ingredient(id = "41", name = "Broccoli", quantity = "1 head", category = "Produce"),
                    Ingredient(id = "42", name = "Carrots", quantity = "2 medium", category = "Produce"),
                    Ingredient(id = "43", name = "Soy sauce", quantity = "3 tbsp", category = "Condiments"),
                    Ingredient(id = "44", name = "Garlic", quantity = "3 cloves", category = "Produce"),
                    Ingredient(id = "45", name = "Vegetable oil", quantity = "2 tbsp", category = "Pantry")
                ),
                tags = listOf("Asian", "Vegetarian", "Quick")
            ),
            Recipe(
                id = "9",
                title = "French Toast",
                description = "Golden brown bread soaked in sweet custard mixture",
                imageUrl = "https://images.unsplash.com/photo-1484723091739-30a097e8f929?w=400",
                cookTime = 15,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Whisk eggs, milk, vanilla, and cinnamon",
                    "Dip bread slices in mixture",
                    "Cook on buttered griddle until golden",
                    "Flip and cook other side",
                    "Serve with syrup and butter"
                ),
                ingredients = listOf(
                    Ingredient(id = "46", name = "Thick bread slices", quantity = "8 slices", category = "Bakery"),
                    Ingredient(id = "47", name = "Eggs", quantity = "4 large", category = "Dairy"),
                    Ingredient(id = "48", name = "Milk", quantity = "1/2 cup", category = "Dairy"),
                    Ingredient(id = "49", name = "Vanilla extract", quantity = "1 tsp", category = "Pantry"),
                    Ingredient(id = "50", name = "Cinnamon", quantity = "1/2 tsp", category = "Spices"),
                    Ingredient(id = "51", name = "Butter", quantity = "2 tbsp", category = "Dairy")
                ),
                tags = listOf("Breakfast", "French", "Sweet")
            ),
            Recipe(
                id = "10",
                title = "Mushroom Risotto",
                description = "Creamy Italian rice dish with earthy mushrooms",
                imageUrl = "https://images.unsplash.com/photo-1476124369491-e7addf5db371?w=400",
                cookTime = 40,
                servings = 4,
                difficulty = "Hard",
                instructions = listOf(
                    "Sauté mushrooms and set aside",
                    "Toast arborio rice with onions",
                    "Add warm broth one ladle at a time",
                    "Stir constantly until creamy",
                    "Fold in mushrooms, butter, and parmesan"
                ),
                ingredients = listOf(
                    Ingredient(id = "52", name = "Arborio rice", quantity = "1 1/2 cups", category = "Grains"),
                    Ingredient(id = "53", name = "Mixed mushrooms", quantity = "1 lb", category = "Produce"),
                    Ingredient(id = "54", name = "Chicken broth", quantity = "6 cups", category = "Pantry"),
                    Ingredient(id = "55", name = "White wine", quantity = "1/2 cup", category = "Pantry"),
                    Ingredient(id = "56", name = "Parmesan cheese", quantity = "1 cup grated", category = "Dairy"),
                    Ingredient(id = "57", name = "Butter", quantity = "4 tbsp", category = "Dairy")
                ),
                tags = listOf("Italian", "Rice", "Vegetarian")
            ),
            Recipe(
                id = "11",
                title = "BBQ Pulled Pork",
                description = "Slow-cooked pork shoulder in tangy barbecue sauce",
                imageUrl = "https://images.unsplash.com/photo-1558030006-450675393462?w=400",
                cookTime = 480,
                servings = 8,
                difficulty = "Medium",
                instructions = listOf(
                    "Season pork shoulder with dry rub",
                    "Slow cook for 6-8 hours until tender",
                    "Shred meat with forks",
                    "Mix with barbecue sauce",
                    "Serve on buns with coleslaw"
                ),
                ingredients = listOf(
                    Ingredient(id = "58", name = "Pork shoulder", quantity = "4 lbs", category = "Meat"),
                    Ingredient(id = "59", name = "BBQ sauce", quantity = "2 cups", category = "Condiments"),
                    Ingredient(id = "60", name = "Brown sugar", quantity = "1/4 cup", category = "Pantry"),
                    Ingredient(id = "61", name = "Paprika", quantity = "2 tbsp", category = "Spices"),
                    Ingredient(id = "62", name = "Hamburger buns", quantity = "8 count", category = "Bakery"),
                    Ingredient(id = "63", name = "Coleslaw mix", quantity = "1 bag", category = "Produce")
                ),
                tags = listOf("BBQ", "Pork", "Slow-cooked")
            ),
            Recipe(
                id = "12",
                title = "Caprese Salad",
                description = "Simple Italian salad with fresh mozzarella, tomatoes, and basil",
                imageUrl = "https://images.unsplash.com/photo-1608897013039-887f21d8c804?w=400",
                cookTime = 5,
                servings = 4,
                difficulty = "Easy",
                instructions = listOf(
                    "Slice tomatoes and mozzarella",
                    "Arrange alternating on plate",
                    "Tuck fresh basil leaves between",
                    "Drizzle with balsamic and olive oil",
                    "Season with salt and pepper"
                ),
                ingredients = listOf(
                    Ingredient(id = "64", name = "Fresh mozzarella", quantity = "8 oz", category = "Dairy"),
                    Ingredient(id = "65", name = "Large tomatoes", quantity = "2 whole", category = "Produce"),
                    Ingredient(id = "66", name = "Fresh basil", quantity = "1/4 cup", category = "Produce"),
                    Ingredient(id = "67", name = "Balsamic vinegar", quantity = "2 tbsp", category = "Pantry"),
                    Ingredient(id = "68", name = "Extra virgin olive oil", quantity = "2 tbsp", category = "Pantry")
                ),
                tags = listOf("Italian", "Vegetarian", "No-cook")
            )
        )
    }
}