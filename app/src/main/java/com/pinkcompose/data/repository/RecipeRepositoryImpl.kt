package com.pinkcompose.data.repository

import com.pinkcompose.data.mapper.toDomain
import com.pinkcompose.data.remote.ApiService
import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.domain.model.RecipeDetails
import com.pinkcompose.domain.model.RecipeList
import com.pinkcompose.domain.repository.RecipeRepository
import com.pinkcompose.util.Resource
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(private val apiService: ApiService): RecipeRepository {

    override suspend fun getCategories(): Resource<List<RecipeCategory>> {
        return try {
            val response = apiService.getCategories()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.map { it.toDomain() })
            } else {
                Resource.Error("Failed to fetch categories: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getAllRecipeList(): Resource<List<RecipeList>> {
        return try {
            val response = apiService.getRecipeList()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.map { it.toDomain() })
            } else {
                Resource.Error("Failed to fetch recipes: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getRecipeList(categoryId: Int): Resource<List<RecipeList>> {
        return try {
            val response = apiService.getRecipeList()
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!.filter { it.categoryId == categoryId }
                Resource.Success(result.map { it.toDomain() })
            } else {
                Resource.Error("Failed to fetch recipes: ${response.message()}")
            }
        }catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getRecipeDetails(
        recipeId: Int
    ): Resource<RecipeDetails> {

        return try {
            val response = apiService.getRecipeDetails()

            if (response.isSuccessful && response.body() != null) {

                val recipe = response.body()
                    ?.firstOrNull { it.id == recipeId }

                if (recipe != null) {
                    Resource.Success(recipe.toDomain())
                } else {
                    Resource.Error("Recipe not found")
                }

            } else {
                Resource.Error(response.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

}