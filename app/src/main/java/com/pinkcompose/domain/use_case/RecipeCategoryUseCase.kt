package com.pinkcompose.domain.use_case

import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.domain.repository.RecipeRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class RecipeCategoryUseCase @Inject constructor(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke(): Resource<List<RecipeCategory>> {
        return recipeRepository.getCategories()
    }
}