package com.pinkcompose.data.remote.dto.recipe_details

data class RecipeDetailsRes(
    val id: Int,
    val title: String,
    val image: String,
    val duration: String,
    val ingredients: List<String>,
    val steps: List<RecipeStepsRes>
)

data class RecipeStepsRes(
    val step: String,
    val description: String,
    val image: String
)