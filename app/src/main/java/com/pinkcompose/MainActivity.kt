package com.pinkcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pinkcompose.presentation.login.LoginScreen
import com.pinkcompose.presentation.SplashScreen
import com.pinkcompose.presentation.screens.MainScreens
import com.pinkcompose.presentation.screens.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val userData by viewModel.userData.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "splash") {

                composable("splash") { SplashScreen(navController) }

                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("main_screens") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("main_screens") {
                    LaunchedEffect(userData) {
                        if (userData == null) {
                            navController.navigate("login") {
                                popUpTo("main_screens") { inclusive = true }
                            }
                        }
                    }
                    MainScreens()
                }
            }
        }
    }
}
