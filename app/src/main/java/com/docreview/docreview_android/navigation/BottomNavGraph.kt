package com.docreview.docreview_android.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.docreview.docreview_android.model.Login
import com.docreview.docreview_android.screens.*
import com.docreview.docreview_android.screens.comments.Conversation
import com.google.gson.Gson

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
       //composable(route = NavBarScreen.Splash.route){
       //    AnimatedSplashScreen(navController = navController)
       //}
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }

        composable(route = NavBarScreen.Home.route) {
            BackHandler(true) {
            }

            Homepage(navController = navController)
        }
        composable(route = NavBarScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = NavBarScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = NavBarScreen.Comments.route) {
            Conversation(navController = navController)
        }
        composable(route = NavBarScreen.CommentsGraph.route) {
            CommentsGraphpage(navController = navController)
        }
        composable(route = NavBarScreen.UsersGraph.route) {
            UsersGraphpage(navController = navController)
        }
        composable(route = NavBarScreen.OtherGraph.route) {
            OtherGraphpage(navController = navController)
        }
        composable(route = NavBarScreen.CommentsGraph.route) {
            CommentsGraphpage(navController = navController)
        }
    }
}