package com.pinkcompose.data.remote

import com.pinkcompose.data.remote.dto.login.LoginResponse
import com.pinkcompose.data.remote.dto.recipe_category.RecipeCategoryRes
import com.pinkcompose.data.remote.dto.recipe_details.RecipeDetailsRes
import com.pinkcompose.data.remote.dto.recipe_list.RecipeListRes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<LoginResponse>>

    @GET("categories")
    suspend fun getCategories(): Response<List<RecipeCategoryRes>>

    @GET("recipes")
    suspend fun getRecipeList(): Response<List<RecipeListRes>>

    @GET("recipes")
    suspend fun getRecipeDetails(): Response<List<RecipeDetailsRes>>

}
