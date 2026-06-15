package com.pinkcompose.data.mapper

import com.pinkcompose.data.remote.dto.recipe_category.RecipeCategoryRes
import com.pinkcompose.domain.model.RecipeCategory

fun RecipeCategoryRes.toDomain() : RecipeCategory {
    return RecipeCategory(
        id = id,
        name = name,
        icon = icon
    )
}