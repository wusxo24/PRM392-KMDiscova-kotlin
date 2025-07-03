package com.example.kmd.presentation.screens.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(
    onBackClick: () -> Unit,
    viewModel: BookingDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.errorMessage != null -> Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                uiState.bookingDetail != null -> BookingDetailContent(uiState.bookingDetail!!)
            }
        }
    }
}

@Composable
fun BookingDetailContent(booking: com.example.kmd.domain.model.BookingDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DetailCard(title = "Appointment Info", icon = Icons.Default.Event) {
            InfoRow(label = "Status", value = booking.appointmentStatus)
            InfoRow(label = "Session Type", value = booking.sessionType)
            InfoRow(label = "Starts", value = booking.scheduledStartTime)
            InfoRow(label = "Ends", value = booking.scheduledEndTime)
            InfoRow(label = "Duration", value = "${booking.durationHours} hours")
        }

        DetailCard(title = "Participants", icon = Icons.Default.People) {
            InfoRow(label = "Psychologist", value = booking.psychologistName)
            InfoRow(label = "Child", value = booking.childName)
            InfoRow(label = "Parent", value = booking.parentName)
        }

        if (!booking.parentNotes.isNullOrBlank()) {
            DetailCard(title = "Notes", icon = Icons.Default.Notes) {
                Text(text = booking.parentNotes, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                content()
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row {
        Text("$label: ", fontWeight = FontWeight.SemiBold)
        Text(value)
    }
}