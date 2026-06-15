package com.pinkcompose.presentation.recipe_details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.pinkcompose.domain.model.RecipeDetails
import com.pinkcompose.domain.model.RecipeSteps

@Composable
fun RecipeDetailsScreen(
    navController: NavController, viewModel: RecipeDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            RecipeDetailsTopBar(
                navController,
                state.value.recipeDetails?.title ?: ""
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            RecipeDetails(state.value.recipeDetails)
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = colorResource(R.color.pink)
                )
            }
        }
    }
}

@Composable
fun RecipeDetailsTopBar(navController: NavController, recipeName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = colorResource(R.color.gold))
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back Image",
            modifier = Modifier
                .size(24.dp)
                .clickable(true, onClick = { navController.popBackStack() })
        )

        Text(
            text = recipeName,
            fontSize = 20.sp,
            color = colorResource(R.color.black),
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun RecipeDetails(recipeDetails: RecipeDetails?) {

    val steps = recipeDetails?.steps ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            color = colorResource(R.color.gold),
                            shape = RoundedCornerShape(
                                bottomStart = 200.dp,
                                bottomEnd = 200.dp
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(180.dp)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            width = 3.dp,
                            color = colorResource(R.color.black),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .shadow(6.dp)
                ) {

                    AsyncImage(
                        model = recipeDetails?.image,
                        contentDescription = "Recipe Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_no_image),
                        error = painterResource(R.drawable.ic_no_image)
                    )
                }
            }
        }

        item {

            Text(
                text = "⏱ ${recipeDetails?.duration ?: ""}",
                fontSize = 18.sp,
                color = colorResource(R.color.black),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {

            Text(
                text = "Ingredients",
                fontSize = 20.sp,
                color = colorResource(R.color.black),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 24.dp,
                        bottom = 12.dp
                    )
            )
        }

        item {

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(recipeDetails?.ingredients ?: emptyList()) { ingredient ->

                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.gold),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    ) {

                        Text(
                            text = ingredient,
                            fontSize = 14.sp,
                            color = colorResource(R.color.black),
                            fontFamily = FontFamily(Font(R.font.poppins_regular))
                        )
                    }
                }
            }
        }

        item {

            Text(
                text = "Cooking Steps",
                fontSize = 20.sp,
                color = colorResource(R.color.black),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        top = 24.dp,
                        bottom = 12.dp
                    )
            )
        }

        item {

            RecipeStepsSection(
                steps = steps
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun RecipeStepsSection(
    steps: List<RecipeSteps>,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {

    if (steps.isEmpty()) return

    val currentStep =
        viewModel.currentStep.collectAsState().value

    val step = steps[currentStep]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .alpha(
                        if (currentStep > 0) 1f else 0.3f
                    )
                    .clickable(
                        enabled = currentStep > 0
                    ) {
                        viewModel.previousStep()
                    }
            )

            AsyncImage(
                model = step.image,
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_no_image),
                error = painterResource(R.drawable.ic_no_image)
            )

            Icon(
                painter = painterResource(R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .alpha(
                        if (currentStep < steps.lastIndex) 1f else 0.3f
                    )
                    .clickable(
                        enabled = currentStep < steps.lastIndex
                    ) {
                        viewModel.nextStep(
                            steps.lastIndex
                        )
                    }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Step ${step.step}",
            fontSize = 18.sp,
            color = colorResource(R.color.black),
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.poppins_medium))
        )

        Text(
            text = step.description,
            fontSize = 14.sp,
            color = colorResource(R.color.black),
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}