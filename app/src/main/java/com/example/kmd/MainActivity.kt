package com.example.kmd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kmd.presentation.navigation.Screen
import com.example.kmd.presentation.screens.auth.LoginScreen
import com.example.kmd.presentation.screens.HomeScreen
import com.example.kmd.presentation.screens.auth.RegisterScreen
import com.example.kmd.presentation.screens.splash.SplashScreen
import com.example.kmd.ui.theme.KmdTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.kmd.presentation.screens.psychologist.PsychologistListScreen
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

//                        // 👇 Detail screen
//                        composable("${Screen.PsychologistDetails.route}/{id}") { backStackEntry ->
//                            val psychologistId = backStackEntry.arguments?.getString("id") ?: return@composable
//                            PsychologistDetailScreen(psychologistId = psychologistId)
//                        }

                    }
                }
            }
        }
    }
}