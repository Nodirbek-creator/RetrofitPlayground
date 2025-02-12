package com.example.retrofitnagibation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofitnagibation.network.ApiService
import com.example.retrofitnagibation.ui.user_interface.Auth
import com.example.retrofitnagibation.ui.user_interface.HomeScreen
import com.example.retrofitnagibation.ui.user_interface.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String,
    apiService: ApiService
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("home"){
            HomeScreen(
                navController = navController,
                apiService = apiService
            )
        }
        composable("splash"){
            SplashScreen(
                navController = navController
            )
        }
        composable("auth"){
            Auth(
                navController = navController,
                apiService = apiService,
            )
        }

    }
}