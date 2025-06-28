package com.example.kmd.presentation.screens.parent

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kmd.presentation.components.AppTopAppBar
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentProfileScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: ParentProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "My Profile",
                scope = scope,
                drawerState = drawerState
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else if (uiState.parentProfile != null) {
                val profile = uiState.parentProfile!!
                Text("Name: ${profile.fullName}", style = MaterialTheme.typography.headlineSmall)
                Text("Phone: ${profile.phoneNumber}", style = MaterialTheme.typography.bodyLarge)
                Text("Address: ${profile.addressLine1}", style = MaterialTheme.typography.bodyLarge)
                Text("City: ${profile.city}", style = MaterialTheme.typography.bodyLarge)
            } else {
                Text(uiState.errorMessage ?: "Could not load profile")
            }
        }
    }
}