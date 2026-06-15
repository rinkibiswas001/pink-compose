package com.pinkcompose.domain.repository

import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.domain.model.RecipeDetails
import com.pinkcompose.domain.model.RecipeList
import com.pinkcompose.util.Resource

interface RecipeRepository {

    suspend fun getCategories(): Resource<List<RecipeCategory>>

    suspend fun getAllRecipeList(): Resource<List<RecipeList>>

    suspend fun getRecipeList(categoryId: Int): Resource<List<RecipeList>>

    suspend fun getRecipeDetails(recipeId: Int): Resource<RecipeDetails>
}