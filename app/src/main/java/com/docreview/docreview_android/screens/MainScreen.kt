package com.docreview.docreview_android.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.docreview.docreview_android.navigation.BottomNavGraph
import com.docreview.docreview_android.navigation.NavBarScreen



@Composable
fun MainScreen() {
   val navController = rememberNavController()
   Scaffold(
       bottomBar = {
          if (currentRoute(navController) != "login"){
               BottomNavBar(navController = navController)
           }
       }
   ) {
       BottomNavGraph(navController = navController)
   }
}
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun BottomNavBar(navController: NavHostController) {
   val screens = listOf(
       NavBarScreen.Home,
       NavBarScreen.Profile,
       NavBarScreen.Settings,
   )
   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentDestination = navBackStackEntry?.destination

   BottomNavigation {
       screens.forEach { screen ->
           AddItem(
               screen = screen,
               currentDestination = currentDestination,
               navController = navController
           )
       }
   }
}

@Composable
fun RowScope.AddItem(
   screen: NavBarScreen,
   currentDestination: NavDestination?,
   navController: NavHostController
) {
   BottomNavigationItem(
       label = {
           Text(text = screen.title, color = Color.Black)
       },
       icon = {
           Icon(
               imageVector = screen.icon!!,
               contentDescription = "Navigation Icon",
               tint = Color.Black
           )
       },
       selected = currentDestination?.hierarchy?.any {
           it.route == screen.route
       } == true,
       unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
       onClick = {
           navController.navigate(screen.route) {
               popUpTo(navController.graph.findStartDestination().id)
               launchSingleTop = true
           }
       }
   )
}
