package com.example.kmd.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kmd.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onLogout: () -> Unit
) {
    val items = listOf(
        DrawerItem("Profile", Icons.Default.Home, Screen.ParentProfile.route),
        // This is the fix: Changed Screen.PsychologistMarket.route to Screen.PsychologistList.route
        DrawerItem("Psychologists", Icons.Default.People, Screen.PsychologistList.route),
        DrawerItem("My Children", Icons.Default.Person, Screen.ChildrenManage.route)
    )

    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = false,
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(item.route)
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        Spacer(Modifier.height(12.dp))
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Logout, contentDescription = "Logout") },
            label = { Text("Logout") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.close()
                }
                onLogout()
            },
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

data class DrawerItem(val title: String, val icon: ImageVector, val route: String)