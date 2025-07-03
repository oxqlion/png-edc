package com.example.palmscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.palmscanner.ui.theme.PalmScannerTheme
import com.example.palmscanner.view.HomeView
import com.example.palmscanner.view.IncomeView
import com.example.palmscanner.view.ProfileView
import com.example.palmscanner.view.components.BottomNavigationBar
import com.example.palmscanner.view.components.BottomNavigationBarItems

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHostContainer(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationBarItems.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavigationBarItems.Home.route) { HomeView() }
        composable(BottomNavigationBarItems.Transactions.route) { IncomeView() }
        composable(BottomNavigationBarItems.Profile.route) { ProfileView() }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    PalmScannerTheme {
        MainScreen()
    }
}