package com.pinkcompose.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.pinkcompose.R
import com.pinkcompose.presentation.screens.MainViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val offsetY = remember { Animatable(-500f) } // start above the screen
    val rotation = remember { Animatable(0f) }  // start with no rotation
    val userData by viewModel.userData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        // Slide down to center
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000)
        )

        // Small pause before flip
        delay(500)

        // Flip animation (rotate 0 -> 180 degrees)
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 800)
        )

        delay(1000)

        // Decide destination based on login status
        val destination = if (userData != null) "main_screens" else "login"

        // Navigate to appropriate screen
        navController.navigate(destination) {
            popUpTo("splash") { inclusive = true } // remove splash from back stack
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.bg_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_recipe_book),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .offset { IntOffset(0, offsetY.value.dp.roundToPx()) }
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 8 * density // prevents distortion
                }
        )
    }
}

