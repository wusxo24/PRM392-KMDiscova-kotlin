package com.example.kmd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kmd.presentation.navigation.Screen
import com.example.kmd.presentation.screens.auth.LoginScreen
import com.example.kmd.presentation.screens.HomeScreen
import com.example.kmd.presentation.screens.auth.RegisterScreen
import com.example.kmd.presentation.screens.psychologist.PsychologistDetailScreen
import com.example.kmd.presentation.screens.splash.SplashScreen
import com.example.kmd.ui.theme.KmdTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.kmd.presentation.screens.psychologist.PsychologistListScreen
import com.example.kmd.presentation.screens.psychologist.SlotViewScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmdTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                },
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(
                                onNavigateToRegister = {
                                    navController.navigate(Screen.Register.route)
                                },
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Register.route) {
                            RegisterScreen(
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                },
                                onNavigateToHome = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Register.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // 👇 Psychologist List (main screen after login)
                        composable(Screen.Home.route) {
                            PsychologistListScreen(
                                onPsychologistClick = { psychologistId ->
                                    navController.navigate("${Screen.PsychologistDetails.route}/$psychologistId")
                                }
                            )
                        }

                        composable(
                            route = "${Screen.PsychologistDetails.route}/{psychologistId}",
                            arguments = listOf(navArgument("psychologistId") { type = NavType.StringType })
                        ) {
                            PsychologistDetailScreen(
                                onBackClick = { navController.popBackStack() },
                                onBookClick = { id, type ->
                                    navController.navigate("booking_screen/$id/$type")
                                }
                            )

                        }
                        composable(
                            route = "booking_screen/{userId}/{sessionType}",
                            arguments = listOf(
                                navArgument("userId") { type = NavType.StringType },
                                navArgument("sessionType") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId") ?: ""
                            val sessionType = backStackEntry.arguments?.getString("sessionType") ?: ""

                            SlotViewScreen(
                                userId = userId,
                                sessionType = sessionType,
                                onBackClick = { navController.popBackStack() }
                            )
                        }

                    }
                }
            }
        }
    }
}