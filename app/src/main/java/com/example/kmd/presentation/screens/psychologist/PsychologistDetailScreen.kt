package com.example.kmd.presentation.screens.psychologist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.kmd.domain.model.Child
import com.example.kmd.domain.model.PsychologistDetail
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import android.location.Geocoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsychologistDetailScreen(
    onBackClick: () -> Unit,
    onBookClick: (String, String, String) -> Unit, // Add childId parameter
    onChatClick: (String, String) -> Unit, // Add chat navigation parameter
    viewModel: PsychologistDetailViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val state = viewModel.uiState
    val children = viewModel.childrenState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state is PsychologistDetailViewModel.DetailUiState.Success) {
                            state.psychologist.fullName
                        } else {
                            "Psychologist Details"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (state) {
                is PsychologistDetailViewModel.DetailUiState.Loading -> {
                    LoadingState()
                }

                is PsychologistDetailViewModel.DetailUiState.Error -> {
                    ErrorState(message = state.message)
                }

                is PsychologistDetailViewModel.DetailUiState.Success -> {
                    PsychologistContent(
                        psychologist = state.psychologist,
                        children = children,
                        onBookClick = onBookClick,
                        onChatClick = onChatClick
                    )
                }
            }
        }
    }
}
@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading psychologist details...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onErrorContainer,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
private fun PsychologistContent(
    psychologist: PsychologistDetail,
    children: List<Child>,
    onBookClick: (String, String, String) -> Unit,
    onChatClick: (String, String) -> Unit
) {
    var selectedChild by remember { mutableStateOf<Child?>(null) }
    var showAddressDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    // For geocoding and map state
    var latLng by remember { mutableStateOf<LatLng?>(null) }
    var isGeocoding by remember { mutableStateOf(false) }
    var geocodeError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val painter = rememberAsyncImagePainter(model = psychologist.profilePictureUrl)
        val hasError = painter.state is AsyncImagePainter.State.Error
        val hasUrl = !psychologist.profilePictureUrl.isNullOrBlank()

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (hasUrl && !hasError) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default avatar",
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Address Button
        if (!psychologist.officeAddress.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                showAddressDialog = true
                isGeocoding = true
                geocodeError = null
                coroutineScope.launch {
                    val result = geocodeAddress(context, psychologist.officeAddress!!)
                    if (result != null) {
                        latLng = result
                        geocodeError = null
                    } else {
                        geocodeError = "Could not find location."
                    }
                    isGeocoding = false
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("View Address")
            }
        }

        // Chat Button
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onChatClick(psychologist.id, psychologist.fullName) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Chat with ${psychologist.fullName}")
        }

        if (showAddressDialog) {
            AlertDialog(
                onDismissRequest = { showAddressDialog = false },
                title = { Text("Office Address") },
                text = {
                    Column {
                        Text(psychologist.officeAddress ?: "No address available")
                        Spacer(modifier = Modifier.height(8.dp))
                        when {
                            isGeocoding -> {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Loading map...")
                                }
                            }
                            geocodeError != null -> {
                                Text(geocodeError!!, color = MaterialTheme.colorScheme.error)
                            }
                            latLng != null -> {
                                val currentLatLng = latLng!!
                                val cameraPositionState = rememberCameraPositionState()
                                LaunchedEffect(currentLatLng) {
                                    cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 16f)
                                }
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)) {
                                    GoogleMap(
                                        modifier = Modifier.matchParentSize(),
                                        cameraPositionState = cameraPositionState
                                    ) {
                                        Marker(
                                            state = MarkerState(position = currentLatLng),
                                            title = "Office"
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showAddressDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        // Content Section
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Biography Section
            if (!psychologist.biography.isNullOrBlank()) {
                InfoSection(
                    title = "About",
                    content = {
                        Text(
                            text = psychologist.biography,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                        )
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Details Section
            InfoSection(
                title = "Professional Details",
                content = {
                    DetailRow(
                        icon = Icons.Default.Star,
                        label = "Experience",
                        value = "${psychologist.yearsOfExperience} years"
                    )

                    psychologist.hourlyRate?.let { rate ->
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow(
                            icon = Icons.Default.AttachMoney,
                            label = "Hourly Rate",
                            value = "$$rate"
                        )
                    }


                    psychologist.initialConsultationRate?.let { rate ->
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailRow(
                            icon = Icons.Default.AttachMoney,
                            label = "initialConsultationRate",
                            value = "$$rate"
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DetailRow(
                        icon = Icons.Default.Psychology,
                        label = "Initial Consultation",
                        value = if (psychologist.services.contains("Initial Consultations")) "Available" else "Not Available"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(
                        icon = Icons.Default.Psychology,
                        label = "Online Session",
                        value = if (psychologist.services.contains("Online Sessions")) "Available" else "Not Available"
                    )
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
            // Child Dropdown
            ChildrenDropdown(
                children = children,
                selectedChild = selectedChild,
                onChildSelected = { selectedChild = it }
            )
// Booking Buttons
            if (psychologist.offersInitialConsultation) {
                Button(
                    onClick = {
                        selectedChild?.let {
                            onBookClick(psychologist.id, "InitialConsultation", it.id)
                        }
                    },
                    enabled = selectedChild != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Book Initial Consultation",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (psychologist.offersOnlineSessions) {
                Button(
                    onClick = {
                        selectedChild?.let {
                            onBookClick(psychologist.id, "OnlineMeeting", it.id)
                        }
                    },
                    enabled = selectedChild != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Book Online Session",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // optional spacing
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}
@Composable
fun ChildrenDropdown(
    children: List<Child>,
    selectedChild: Child?,
    onChildSelected: (Child) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedChild?.displayName ?: "Select a child",
            onValueChange = { },
            readOnly = true,
            label = { Text("Child") },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            children.forEach { child ->
                DropdownMenuItem(
                    text = { Text(child.displayName) },
                    onClick = {
                        onChildSelected(child)
                        expanded = false
                    }
                )
            }
        }
    }
}

// Helper function for geocoding
suspend fun geocodeAddress(context: android.content.Context, address: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context)
            val results = geocoder.getFromLocationName(address, 1)
            if (!results.isNullOrEmpty()) {
                val loc = results[0]
                LatLng(loc.latitude, loc.longitude)
            } else null
        } catch (e: Exception) {
            null
        }
    }
}