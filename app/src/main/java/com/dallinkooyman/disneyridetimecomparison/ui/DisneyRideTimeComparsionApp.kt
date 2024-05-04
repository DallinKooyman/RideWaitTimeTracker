package com.dallinkooyman.disneyridetimecomparison.ui

import android.content.res.Resources.Theme
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.ui.theme.secondaryContainerDark

enum class AppScreens(@StringRes val title: Int) {
    Home(title = R.string.home_screen)
}

@Composable
fun DisneyRideTimeComparisonApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreens.valueOf(
        backStackEntry?.destination?.route ?: AppScreens.Home.name
    )
    Scaffold(
        bottomBar = {
            BottomNavBar(
                canNavigateBack = true,
                navigateUp = { /*TODO*/ },
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.Home.name){
                HomeScreen()
            }
        }
    }
}


@Composable
fun BottomNavBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreen: AppScreens,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painterResource(id = R.drawable.icons8_bar_chart_100), contentDescription = "Stats Icon")
//            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Home, contentDescription = "Home Page")
            }
        },
        containerColor = secondaryContainerDark

    )
}

@Preview
@Composable
fun BottomNavBarPreview(){
    BottomNavBar(
        canNavigateBack = true,
        navigateUp = { /*TODO*/ },
        currentScreen = AppScreens.Home)
}
