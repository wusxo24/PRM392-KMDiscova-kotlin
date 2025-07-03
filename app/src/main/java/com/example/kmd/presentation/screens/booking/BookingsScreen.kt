package com.example.kmd.presentation.screens.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kmd.domain.model.Booking
import com.example.kmd.presentation.components.AppTopAppBar
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(
    scope: CoroutineScope,
    drawerState: DrawerState,
    onBookingClick: (String) -> Unit, // Add this callback
    viewModel: BookingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = "My Bookings",
                scope = scope,
                drawerState = drawerState
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (uiState.bookings.isEmpty()) {
                Text("No bookings found.", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.bookings) { booking ->
                        BookingItem(booking, onClick = { onBookingClick(booking.appointmentId) }) // Pass the callback
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingItem(booking: Booking, onClick: () -> Unit) { // Add onClick parameter
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick // Make the card clickable
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Psychologist: ${booking.psychologistName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(text = "Child: ${booking.childName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Session: ${booking.sessionType}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${booking.appointmentStatus}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Starts: ${booking.scheduledStartTime}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Ends: ${booking.scheduledEndTime}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}