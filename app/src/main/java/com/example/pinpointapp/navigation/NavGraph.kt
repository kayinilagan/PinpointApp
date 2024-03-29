package com.example.pinpointapp.navigation

import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.keys.Keys.SELECTED_POINTS_KEY
import com.example.pinpointapp.presentation.screen.create.CreateScreen
import com.example.pinpointapp.presentation.screen.data.DataScreen
import com.example.pinpointapp.presentation.screen.details.DetailsScreen
import com.example.pinpointapp.presentation.screen.map.MapScreen
import com.example.pinpointapp.presentation.screen.login.LoginScreen
import com.example.pinpointapp.presentation.screen.pinned.PinnedScreen
import com.example.pinpointapp.presentation.screen.saved.SavedScreen
import com.example.pinpointapp.presentation.screen.submitted.SubmittedScreen

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(
            route = Screen.Login.route,
            arguments = listOf(navArgument(name = "signedInState", builder = {
                type = NavType.BoolType
                defaultValue = true
            }))
        ) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }
        composable(route = Screen.Data.route) {
            DataScreen(navController = navController)
        }
        composable(route = Screen.Details.route) {
            val selectedSet = navController.previousBackStackEntry?.savedStateHandle?.get<PointSet>(
                key = SELECTED_POINTS_KEY
            )
            if (selectedSet != null) {
                DetailsScreen(navController = navController, pointSet = selectedSet)
            }
        }
        composable(route = Screen.Pinned.route) {
            PinnedScreen(navController = navController)
        }
        composable(route = Screen.Saved.route) {
            SavedScreen(navController = navController)
        }
        composable(route = Screen.Submitted.route) {
            SubmittedScreen(navController = navController)
        }
        composable(route = Screen.Create.route) {
            CreateScreen(navController = navController)
        }
    }
}