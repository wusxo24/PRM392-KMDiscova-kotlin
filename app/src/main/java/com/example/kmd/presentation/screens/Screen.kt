package com.example.kmd.presentation.screens

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("psychologists") // Your list screen
    object PsychologistDetail : Screen("psychologist_detail") // Detail screen
}
