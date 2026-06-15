package com.pinkcompose.presentation.recipe_category

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pinkcompose.R
import com.pinkcompose.domain.model.RecipeCategory
import com.pinkcompose.presentation.screens.Screens

@Composable
fun ProdCategoryScreen(
    navController: NavController,
    viewModel: RecipeCategoryViewModel = hiltViewModel()
) {

    val state = viewModel.recipeCategory.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            Toast.makeText(
                context, it, Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Recipe Categories",
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 24.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(state.value.recipeCategory.size) { index ->
                    RecipeCategoryItem(
                        recipeCategory = state.value.recipeCategory[index],
                        onClick = {
                            val id = state.value.recipeCategory[index].id ?: 0
                            val name = state.value.recipeCategory[index].name ?: ""

                            Log.d("NAV_TEST", "Clicked category id = $id")

                            navController.navigate(
                                Screens.RecipeListScreen.createRoute(id, name)
                            )
                        }
                    )
                }
            }
        }

        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), color = colorResource(R.color.pink)
            )
        }
    }
}

@Composable
fun RecipeCategoryItem(
    recipeCategory: RecipeCategory, onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.pink),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() }
        ) {
            AsyncImage(
                model = recipeCategory.icon ?: R.drawable.ic_no_image,
                contentDescription = "Recipe Category Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_no_image),
                error = painterResource(R.drawable.ic_no_image),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            recipeCategory.name?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            }

        }
    }
}