package com.pinkcompose.presentation.screens

import androidx.annotation.DrawableRes
import com.pinkcompose.R

sealed class Screens(
    val screen: String,
    val label: String,
    @DrawableRes val icon: Int
) {

     object HomeScreen : Screens(
        screen = "home_screen",
        label = "Home",
        icon = R.drawable.ic_home
    )

     object ProdCategoryScreen : Screens(
        screen = "prod_category_screen",
        label = "Categories",
        icon = R.drawable.ic_category
    )

     object ProfileScreen : Screens(
        screen = "profile_screen",
        label = "Profile",
        icon = R.drawable.ic_profile
    )

     object RecipeListScreen : Screens(
        screen = "recipe_list_screen/{categoryId}/{categoryName}",
        label = "Recipe List",
        icon = R.drawable.ic_home
    ){
         fun createRoute(categoryId: Int, categoryName: String): String {
             return "recipe_list_screen/$categoryId/$categoryName"
         }
     }

    object RecipeDetailsScreen : Screens(
        screen = "recipe_details_screen/{recipeId}",
        label = "Recipe Details",
        icon = R.drawable.ic_home
    ){
        fun createRoute(recipeId: Int): String {
            return "recipe_details_screen/$recipeId"
        }
    }

}