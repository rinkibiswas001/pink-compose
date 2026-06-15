package com.pinkcompose.data.mapper

import com.pinkcompose.data.remote.dto.recipe_list.RecipeListRes
import com.pinkcompose.domain.model.RecipeList

fun RecipeListRes.toDomain(): RecipeList {
    return RecipeList(
        id = id,
        categoryId = categoryId,
        title = title,
        image = image
    )
}