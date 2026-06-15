package com.pinkcompose.presentation.recipe_list

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pinkcompose.R
import com.pinkcompose.domain.model.RecipeList
import com.pinkcompose.presentation.screens.Screens

@Composable
fun RecipeListScreen(
    categoryName: String,
    navController: NavController,
    viewModel: RecipeListViewModel = hiltViewModel()
) {

    val state = viewModel.recipeList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            Toast.makeText(
                context, it, Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = { ListTopBar(categoryName, navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.value.recipeList.size) { index ->
                    RecipeListItem(
                        recipe = state.value.recipeList[index],
                        onClick = {
                            val id = state.value.recipeList[index].id

                            navController.navigate(
                                Screens.RecipeDetailsScreen.createRoute(id)
                            )
                        }
                    )
                }
            }

            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = colorResource(R.color.pink)
                )
            }

        }
    }
}

@Composable
fun ListTopBar(categoryName: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = colorResource(R.color.light_pink))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable(true, onClick = { navController.popBackStack() })
        )

        Text(
            text = categoryName,
            fontSize = 20.sp,
            color = colorResource(R.color.black),
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun RecipeListItem(recipe: RecipeList, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() }
    ) {

        Column {
            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_no_image),
                error = painterResource(R.drawable.ic_no_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Text(
                text = recipe.title,
                fontSize = 16.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.light_pink))
                    .padding(12.dp)
            )
        }
    }
}