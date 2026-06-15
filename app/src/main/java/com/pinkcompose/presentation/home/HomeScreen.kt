package com.pinkcompose.presentation.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import kotlinx.coroutines.delay

val bannerImages = listOf(
    "https://images.unsplash.com/photo-1490645935967-10de6ba17061?w=1200",
    "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=1200",
    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=1200"
)

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val state = viewModel.recipes.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                AutoImageSlider(bannerImages)
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.value.recipeCategory) { categories ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.pink),
                                    shape = CircleShape
                                )
                                .size(60.dp)
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(
                                        Screens.RecipeListScreen.createRoute(
                                            categories.id ?: 0,
                                            categories.name ?: ""
                                        )
                                    )
                                }
                        ) {
                            AsyncImage(
                                model = categories.icon,
                                contentDescription = "Category Image",
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(R.drawable.ic_no_image),
                                error = painterResource(R.drawable.ic_no_image)
                            )
                        }
                    }
                }
            }

            items(state.value.recipeList) { recipe ->
                RecipeItem(recipe, navController)
            }
        }

        if (state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(R.color.pink)
            )
        }
    }
}

@Composable
fun AutoImageSlider(
    images: List<String>,
    autoScrollDuration: Long = 3000L
) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    // Auto-scroll effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(autoScrollDuration)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(durationMillis = 600)
            )
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = "Banner Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_no_image),
                error = painterResource(R.drawable.ic_no_image),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dot indicators
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                val isSelected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(width = if (isSelected) 20.dp else 8.dp, height = 8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (isSelected) colorResource(R.color.pink)
                            else colorResource(R.color.pink).copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: RecipeList, navController: NavController) {

    Card(
        modifier = Modifier.fillMaxWidth().clickable{
            navController.navigate(Screens.RecipeDetailsScreen.createRoute(recipe.id))
        },
        shape = RoundedCornerShape(12.dp)
    ) {

        Column {
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = recipe.title,
                fontSize = 16.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.light_pink))
                    .padding(vertical = 12.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}