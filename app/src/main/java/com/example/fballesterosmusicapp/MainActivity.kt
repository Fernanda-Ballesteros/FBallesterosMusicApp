package com.example.fballesterosmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.fballesterosmusicapp.ui.theme.FBallesterosMusicAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.fballesterosmusicapp.screens.HomeScreenRoute
import com.example.fballesterosmusicapp.screens.AlbumDetailScreenRoute
import com.example.fballesterosmusicapp.screens.HomeScreen
import com.example.fballesterosmusicapp.screens.AlbumDetailScreen





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FBallesterosMusicAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(navController = navController, startDestination = HomeScreenRoute){//
                        composable<HomeScreenRoute> {
                            HomeScreen(
                                navController = navController
                            )
                        }
                        composable<AlbumDetailScreenRoute> { backStack ->
                            val args = backStack.toRoute<AlbumDetailScreenRoute>()
                            AlbumDetailScreen(args.id, navController = navController)
                        }
                    }


                }
            }
        }
    }
}



