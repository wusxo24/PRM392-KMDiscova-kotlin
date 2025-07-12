package com.example.kmd.presentation.screens.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Signpost
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
        },
        floatingActionButton = {
            if (!uiState.isUpdating) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FloatingActionButton(
                        onClick = { viewModel.onEditClick() }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                    }
                    
                    FloatingActionButton(
                        onClick = { viewModel.testNotification() }
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Test Notification")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                if (uiState.isLoading || uiState.isUpdating) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (uiState.isUpdating) "Updating profile..." else "Loading profile...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else if (uiState.parentProfile != null) {
                    val profile = uiState.parentProfile!!
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Personal Information Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Personal Information",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ProfileInfoRow(icon = Icons.Default.Person, label = "Full Name", value = profile.fullName)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ProfileInfoRow(icon = Icons.Default.Phone, label = "Phone", value = profile.phoneNumber)
                            }
                        }

                        // Address Information Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Address Information",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ProfileInfoRow(icon = Icons.Default.Business, label = "Address", value = profile.addressLine1)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ProfileInfoRow(icon = Icons.Default.LocationCity, label = "City", value = profile.city)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ProfileInfoRow(icon = Icons.Default.Signpost, label = "State/Province", value = profile.stateProvince)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ProfileInfoRow(icon = Icons.Default.Email, label = "Postal Code", value = profile.postalCode)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                ProfileInfoRow(icon = Icons.Default.Public, label = "Country", value = profile.country)
                            }
                        }
                    }
                } else {
                    Text(uiState.errorMessage ?: "Could not load profile")
                }
            }
        }
    }

    if (uiState.isEditing && uiState.parentProfile != null) {
        EditParentProfileDialog(
            profile = uiState.parentProfile!!,
            onDismiss = { viewModel.onDismissEditDialog() },
            onSave = { editedProfile ->
                viewModel.onSaveProfile(editedProfile)
            }
        )
    }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}