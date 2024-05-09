package com.dallinkooyman.disneyridetimecomparison.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.screens.HomeScreen
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme

enum class AppScreens {
    Home,
    Stats,
    History
}
@Composable
fun DisneyRideTimeComparisonApp(
    viewModel: RideViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreens.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.Home.name){
                HomeScreen(
                    ride = uiState.currentRideEvent,
                    onUpdateRideEventInfo = {viewModel.updateCurrentRideEvent(it)},
                    onOnRideButtonClicked = {
                        viewModel.updateCurrentRide(uiState.currentRide, uiState.currentRideEvent)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = AppScreens.Stats.name){
                HomeScreen(
                    ride = null,
                    onUpdateRideEventInfo = {},
                    onOnRideButtonClicked = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = AppScreens.History.name){
                HomeScreen(
                    ride = null,
                    onUpdateRideEventInfo = {},
                    onOnRideButtonClicked = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
) {
    NavigationBar{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = navBackStackEntry?.destination?.route
        NavigationBarItem(
            selected = AppScreens.Stats.name == currentScreen,
            onClick = { navController.navigate((AppScreens.Stats.name)) },
            icon = {
                if (AppScreens.Stats.name == currentScreen){
                    Icon(
                        painterResource(R.drawable.leaderboard_filled),
                        contentDescription = "Stats Screen",
                        modifier = Modifier.size(40.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.leaderboard),
                        contentDescription = "Stats Screen",
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            label = { Text(text = "Stats")}
        )
        NavigationBarItem(
            selected = AppScreens.Home.name == currentScreen,
            onClick = { navController.navigate((AppScreens.Home.name)) },
            icon = {
                if (AppScreens.Home.name == currentScreen){
                    Icon(
                        painterResource(R.drawable.home_filled),
                        contentDescription = "Main Menu",
                        modifier = Modifier.size(40.dp)
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.home),
                        contentDescription = "Main menu",
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            label = { Text(text = "Home")}
        )
        NavigationBarItem(
            selected = AppScreens.History.name == currentScreen,
            onClick = { navController.navigate((AppScreens.History.name)) },
            icon = {
                if (AppScreens.History.name == currentScreen){
                    Icon(
                        painterResource(R.drawable.history_selected),
                        contentDescription = "History Screen",
                        modifier = Modifier.size(40.dp)                       )
                } else {
                    Icon(
                        painterResource(R.drawable.history),
                        contentDescription = "History Screen",
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            label = { Text(text = "Ride History")}
        )
    }
}

@Preview
@Composable
fun BottomNavBarPreview(){
    val navController = rememberNavController()
    AppTheme{
        BottomNavBar(
            navController = navController
        )
    }

}
