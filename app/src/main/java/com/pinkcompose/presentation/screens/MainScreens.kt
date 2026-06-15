package com.pinkcompose.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pinkcompose.R
import com.pinkcompose.presentation.home.HomeScreen
import com.pinkcompose.presentation.recipe_category.ProdCategoryScreen
import com.pinkcompose.presentation.profile.ProfileScreen
import com.pinkcompose.presentation.recipe_details.RecipeDetailsScreen
import com.pinkcompose.presentation.recipe_list.RecipeListScreen
import kotlinx.coroutines.launch

val navItems = listOf(
    Screens.HomeScreen,
    Screens.ProdCategoryScreen,
    Screens.ProfileScreen,
)

@Composable
fun MainScreens(viewModel: MainViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutAlertDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout()
            }
        )
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route

    val showNavigationUI = navItems.any {
        it.screen == currentRoute
    }

    if (showNavigationUI) {

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(
                    navController,
                    drawerState,
                    userData = userData,
                    onLogout = {
                        showLogoutDialog = true
                    }
                )
            }
        ) {

            Scaffold(
                topBar = { TopBar(drawerState) },
                bottomBar = {
                    BottomNavigationBar(navController)
                }
            ) { innerPadding ->

                MainNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    userData = userData,
                    onLogout = { showLogoutDialog = true }
                )
            }
        }

    } else {

        MainNavHost(
            navController = navController,
            userData = userData,
            onLogout = { showLogoutDialog = true }
        )
    }
}

@Composable
fun LogoutAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Logout")
        },
        text = {
            Text(text = "Are you sure you want to logout?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Yes", color = colorResource(R.color.pink))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "No", color = Color.Gray)
            }
        },
        containerColor = Color.White,
        titleContentColor = Color.Black,
        textContentColor = Color.Black
    )
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userData: com.pinkcompose.domain.model.Login?,
    onLogout: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.screen,
        modifier = modifier
    ) {

        composable(Screens.HomeScreen.screen) {
            HomeScreen(navController)
        }

        composable(Screens.ProdCategoryScreen.screen) {
            ProdCategoryScreen(navController)
        }

        composable(Screens.ProfileScreen.screen) {
            ProfileScreen(
                userData = userData,
                onLogout = onLogout
            )
        }

        composable(
            route = Screens.RecipeListScreen.screen,
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.IntType
                },
                navArgument("categoryName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""

            RecipeListScreen(
                categoryName = categoryName,
                navController = navController
            )
        }

        composable(
            route = Screens.RecipeDetailsScreen.screen,
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.IntType
                }
            )
        ) {
            RecipeDetailsScreen(
                navController = navController
            )
        }
    }
}

@Composable
fun TopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(colorResource(R.color.light_pink))
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_menu),
            contentDescription = "img_menu",
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    scope.launch { drawerState.open() }
                }
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(110.dp)
    ) {

        // Pink Bottom Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.BottomCenter)
                .background(
                    colorResource(R.color.pink),
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp
                    )
                )
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                navItems.forEach { screen ->

                    val isSelected = currentRoute == screen.screen

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                navController.navigate(screen.screen) {
                                    launchSingleTop = true
                                    popUpTo(
                                        navController.graph.startDestinationId
                                    )
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        if (isSelected) {

                            Box(
                                modifier = Modifier
                                    .size(58.dp)
                                    .offset(y = (-30).dp)
                                    .zIndex(1f)
                                    .background(
                                        colorResource(R.color.pink),
                                        CircleShape
                                    )
                                    .border(
                                        width = 4.dp,
                                        color = Color.White,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = screen.label,
                                    tint = colorResource(R.color.white),
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                        } else {

                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = screen.label,
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    userData: com.pinkcompose.domain.model.Login?,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    ModalDrawerSheet(
        drawerContainerColor = colorResource(R.color.light_pink),
        drawerContentColor = colorResource(R.color.black),
        modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Column(
                    modifier = Modifier.weight(1f).padding(8.dp)
                ) {
                    Text(
                        userData?.username ?: "Guest",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        userData?.email ?: "No email",
                        color = Color.Gray
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = "img_logout",
                    modifier = Modifier.clickable { onLogout() }
                )
            }

            HorizontalDivider(
                color = colorResource(R.color.pink),
                thickness = 2.dp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            navItems.forEach { screen ->
                NavigationDrawerItem(
                    modifier = Modifier.padding(vertical = 2.dp),
                    label = { Text(text = screen.label, fontSize = 16.sp) },
                    selected = currentRoute == screen.screen,
                    shape = RoundedCornerShape(12.dp),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            tint = if (currentRoute == screen.screen) colorResource(R.color.pink) else Color.Black,
                            contentDescription = screen.label,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(screen.screen) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.White,
                        selectedTextColor = colorResource(R.color.pink),
                        selectedIconColor = colorResource(R.color.pink),
                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = Color.Black,
                        unselectedIconColor = Color.Black
                    )
                )
            }
        }
    }

}
