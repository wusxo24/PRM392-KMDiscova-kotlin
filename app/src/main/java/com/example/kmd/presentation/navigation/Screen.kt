package com.example.kmd.presentation.navigation


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object PsychologistList : Screen("psychologist_list")
    object PsychologistDetails : Screen("psychologist_details/{psychologistId}") {
        fun createRoute(psychologistId: String) = "psychologist_details/$psychologistId"
    }
    object Booking : Screen("booking")
    object MyAppointments : Screen("my_appointments")
    object AppointmentDetails : Screen("appointment_details/{appointmentId}") {
        fun createRoute(appointmentId: String) = "appointment_details/$appointmentId"
    }
    object CreateParentProfile : Screen("create_parent_profile")
    object ParentProfile : Screen("parent_profile")
    object ChildrenManage : Screen("children_manage")
    object Cart : Screen("cart")

}