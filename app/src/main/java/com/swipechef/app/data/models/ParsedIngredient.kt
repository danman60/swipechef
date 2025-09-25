package com.swipechef.app.data.models

// Normalized ingredient for grocery list aggregation
data class ParsedIngredient(
    val qty: Float? = null,
    val unit: MeasurementUnit? = null,
    val item: String,
    val note: String? = null,
    val category: String = "Other"
)

enum class MeasurementUnit(val displayName: String, val abbreviation: String) {
    // Volume
    TSP("teaspoon", "tsp"),
    TBSP("tablespoon", "tbsp"),
    CUP("cup", "cup"),
    ML("milliliter", "ml"),
    L("liter", "l"),

    // Weight
    G("gram", "g"),
    KG("kilogram", "kg"),
    OZ("ounce", "oz"),
    LB("pound", "lb"),

    // Count
    PIECE("piece", "pc"),
    ITEM("item", "item")
}