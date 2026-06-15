package com.pinkcompose.domain.use_case

import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.domain.model.RecipeList
import com.pinkcompose.domain.repository.RecipeRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {

    suspend fun getRecipeCategory(): Resource<List<RecipeCategory>> {
        return recipeRepository.getCategories()
    }

    suspend fun getAllRecipes(): Resource<List<RecipeList>> {
        return recipeRepository.getAllRecipeList()
    }
}