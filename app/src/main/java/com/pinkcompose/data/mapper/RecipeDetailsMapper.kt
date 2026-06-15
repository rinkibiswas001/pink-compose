package com.pinkcompose.data.mapper

import com.pinkcompose.data.remote.dto.recipe_details.RecipeDetailsRes
import com.pinkcompose.data.remote.dto.recipe_details.RecipeStepsRes
import com.pinkcompose.domain.model.RecipeDetails
import com.pinkcompose.domain.model.RecipeSteps

fun RecipeDetailsRes.toDomain(): RecipeDetails{
    return RecipeDetails(
        id = id,
        title = title,
        image = image,
        duration = duration,
        ingredients = ingredients,
        steps = steps.map { it.toDomain() }
    )
}

fun RecipeStepsRes.toDomain(): RecipeSteps {
    return RecipeSteps(
        step = step,
        description = description,
        image = image
    )
}