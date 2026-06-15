package com.pinkcompose.domain.model

data class RecipeDetails(
    val id: Int,
    val title: String,
    val image: String,
    val duration: String,
    val ingredients: List<String>,
    val steps: List<RecipeSteps>
)

data class RecipeSteps(
    val step: String,
    val description: String,
    val image: String
)