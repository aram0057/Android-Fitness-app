package com.example.fit5046_project

import RegisterScreen
import RegisterSuccessfulScreen
import android.app.Application
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(navController: NavController, email: String) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    // Define the destinations where you want to hide the bottom navigation bar
    val destinationsWithoutBottomBar = setOf(
        Routes.Welcome.value,
        Routes.LoginScreen.value,
        Routes.RegisterScreen.value,
        Routes.RegisterSuccessfulScreen.value,
        Routes.LogoutScreen.value
    )

    // Check if the current destination should hide the bottom navigation bar
    val hideBottomBar = currentDestination?.route in destinationsWithoutBottomBar

    if (!hideBottomBar) {
        BottomNavigation(backgroundColor = Color.LightGray) {
            NavBarItem().NavBarItems().forEach { navItem ->
                BottomNavigationItem(
                    icon = {
                        androidx.compose.material.Icon(
                            navItem.icon,
                            contentDescription = null
                        )
                    },
                    label = { androidx.compose.material.Text(navItem.label) },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == navItem.route
                    } == true,
                    onClick = {
                        navController.navigate(navItem.route)
                    }
                )
            }
        }
    }
}