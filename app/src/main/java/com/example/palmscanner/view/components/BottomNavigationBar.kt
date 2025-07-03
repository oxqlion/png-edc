package com.example.palmscanner.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

sealed class BottomNavigationBarItems(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavigationBarItems("home", Icons.Filled.Home, "Home")
    object Transactions : BottomNavigationBarItems("transactions", Icons.Filled.List, "Transactions")
    object Profile : BottomNavigationBarItems("profile", Icons.Filled.Person, "Profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationBarItems.Home,
        BottomNavigationBarItems.Transactions,
        BottomNavigationBarItems.Profile
    )
}