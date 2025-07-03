    package com.example.kmd

    import android.os.Bundle
    import android.widget.Toast
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.material3.*
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.platform.LocalContext
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.example.kmd.presentation.components.AppDrawerContent
    import com.example.kmd.presentation.navigation.Screen
    import com.example.kmd.presentation.screens.auth.LoginScreen
    import com.example.kmd.presentation.screens.auth.RegisterScreen
    import com.example.kmd.presentation.screens.cart.CartDetailScreen
    import com.example.kmd.presentation.screens.cart.CartDetailViewModel
    import com.example.kmd.presentation.screens.cart.CartScreen
    import com.example.kmd.presentation.screens.cart.PaymentScreen
    import com.example.kmd.presentation.screens.children.ChildrenManageScreen
    import com.example.kmd.presentation.screens.parent.CreateParentProfileScreen
    import com.example.kmd.presentation.screens.parent.ParentProfileScreen
    import com.example.kmd.presentation.screens.psychologist.PsychologistDetailScreen
    import com.example.kmd.presentation.screens.psychologist.PsychologistListScreen
    import com.example.kmd.presentation.screens.psychologist.SlotViewScreen
    import com.example.kmd.presentation.screens.booking.BookingsScreen
    import com.example.kmd.presentation.screens.booking.BookingDetailScreen
    import com.example.kmd.presentation.screens.splash.SplashScreen
    import com.example.kmd.ui.theme.KmdTheme
    import dagger.hilt.android.AndroidEntryPoint
    import java.net.URLEncoder
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.setValue
    import com.example.kmd.presentation.screens.chat.ChatListScreen
    import com.example.kmd.presentation.screens.chat.ChatScreen

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {

        @OptIn(ExperimentalMaterial3Api::class)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                KmdTheme {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            AppDrawerContent(
                                navController = navController,
                                scope = scope,
                                drawerState = drawerState,
                                onLogout = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(navController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        NavHost(navController = navController, startDestination = Screen.Splash.route) {
                            composable(Screen.Splash.route) {
                                SplashScreen(
                                    onNavigateToLogin = {
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Splash.route) { inclusive = true }
                                        }
                                    },
                                    onNavigateToHome = {
                                        navController.navigate(Screen.ParentProfile.route) {
                                            popUpTo(Screen.Splash.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(Screen.Login.route) {
                                LoginScreen(
                                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                                    onNavigateToHome = {
                                        navController.navigate(Screen.ParentProfile.route) {
                                            popUpTo(Screen.Login.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(Screen.Register.route) {
                                RegisterScreen(
                                    onNavigateToLogin = { navController.popBackStack() },
                                    onNavigateToHome = {
                                        navController.navigate(Screen.CreateParentProfile.route) {
                                            popUpTo(Screen.Register.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(Screen.CreateParentProfile.route) {
                                CreateParentProfileScreen(
                                    onProfileCreated = {
                                        navController.navigate(Screen.ParentProfile.route) {
                                            popUpTo(Screen.CreateParentProfile.route) { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable(Screen.ParentProfile.route) {
                                ParentProfileScreen(scope = scope, drawerState = drawerState)
                            }
                            composable(Screen.PsychologistList.route) {
                                PsychologistListScreen(
                                    scope = scope,
                                    drawerState = drawerState,
                                    onPsychologistClick = { psychologistId ->
                                        navController.navigate(Screen.PsychologistDetails.createRoute(psychologistId))
                                    },
                                    onLogoutClick = {}
                                )
                            }
                            composable(Screen.ChildrenManage.route) {
                                ChildrenManageScreen(scope = scope, drawerState = drawerState)
                            }
                            composable(Screen.Cart.route) {
                                CartScreen(
                                    scope = scope,
                                    drawerState = drawerState,
                                    onItemClick = { itemId ->
                                        navController.navigate(Screen.CartDetail.createRoute(itemId))
                                    }
                                )
                            }
                            composable(
                                route = Screen.CartDetail.route,
                                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                            ) {
                                CartDetailScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onCheckoutClick = { itemId ->
                                        // Navigate to your checkout screen
                                    }
                                )
                            }
                            composable(
                                route = Screen.PsychologistDetails.route,
                                arguments = listOf(navArgument("psychologistId") { type = NavType.StringType })
                            ) {
                                PsychologistDetailScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onBookClick = { id, type, childId ->
                                        navController.navigate("booking_screen/$id/$type/$childId")
                                    },
                                    onChatClick = { psychologistId, psychologistName ->
                                        navController.navigate(Screen.Chat.createRoute(psychologistId, psychologistName))
                                    }
                                )
                            }
                            composable(
                                route = "booking_screen/{userId}/{sessionType}/{childId}",
                                arguments = listOf(
                                    navArgument("userId") { type = NavType.StringType },
                                    navArgument("sessionType") { type = NavType.StringType },
                                    navArgument("childId") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val userId = backStackEntry.arguments?.getString("userId") ?: ""
                                val sessionType = backStackEntry.arguments?.getString("sessionType") ?: ""
                                val childId = backStackEntry.arguments?.getString("childId") ?: ""

                                SlotViewScreen(
                                    userId = userId,
                                    sessionType = sessionType,
                                    childId = childId,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }

                            composable("cart_detail/{itemId}") { backStackEntry ->
                                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                                val context = LocalContext.current
                                val viewModel: CartDetailViewModel = hiltViewModel()

                                CartDetailScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onCheckoutClick = { itemId ->
                                        viewModel.checkoutAndInitiatePayment(
                                            itemId = itemId,
                                            onSuccess = { paymentResponse ->
                                                // Simply navigate to payment screen with orderId
                                                // The PaymentScreen will handle getting the clientSecret
                                                navController.navigate("payment_screen/${paymentResponse.order.id}")
                                            },
                                            onError = { message ->
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        )
                                    }
                                )
                            }


                            composable(
                                route = "payment_screen/{orderId}",
                                arguments = listOf(navArgument("orderId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                                val context = LocalContext.current

                                PaymentScreen(
                                    orderId = orderId,
                                    onBack = {
                                        // ✅ this line is correct *only if navController is in scope*
                                        navController.popBackStack()
                                    },
                                    onSuccess = {
                                        Toast.makeText(context, "Payment Success!", Toast.LENGTH_SHORT).show()
                                        // Navigate back to appropriate screen after success

                                        navController.navigate(Screen.Cart.route) {
                                            popUpTo("payment_screen/{orderId}") { inclusive = true }
                                        }

                                    },
                                    onFailure = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                            composable(Screen.MyBookings.route) {
                                BookingsScreen(
                                    scope = scope,
                                    drawerState = drawerState,
                                    onBookingClick = { appointmentId ->
                                        navController.navigate(Screen.BookingDetail.createRoute(appointmentId))
                                    }
                                )
                            }

                            composable(
                                route = Screen.BookingDetail.route,
                                arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
                            ) {
                                BookingDetailScreen(
                                    onBackClick = { navController.popBackStack() }
                                )
                            }

                            composable(Screen.ChatList.route) {
                                ChatListScreen(
                                    onBackClick = { navController.popBackStack() },
                                    onChatRoomClick = { chatRoomId ->
                                        // For now, navigate to a generic chat
                                        navController.navigate(Screen.Chat.createRoute(chatRoomId, "Psychologist"))
                                    }
                                )
                            }

                            composable(
                                route = Screen.Chat.route,
                                arguments = listOf(
                                    navArgument("chatRoomId") { type = NavType.StringType },
                                    navArgument("psychologistName") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val chatRoomId = backStackEntry.arguments?.getString("chatRoomId") ?: ""
                                val psychologistName = backStackEntry.arguments?.getString("psychologistName") ?: ""
                                
                                ChatScreen(
                                    chatRoomId = chatRoomId,
                                    psychologistName = psychologistName,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }