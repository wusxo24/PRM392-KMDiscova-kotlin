package com.example.kmd.presentation.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kmd.domain.model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDetailScreen(
    onBackClick: () -> Unit,
    onCheckoutClick: (String) -> Unit,
    viewModel: CartDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart Details") },
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
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                uiState.cartItem != null -> {
                    CartDetailContent(
                        item = uiState.cartItem!!,
                        onDelete = { viewModel.removeCartItem() },
                        onCheckout = { onCheckoutClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun CartDetailContent(
    item: CartItem,
    onDelete: () -> Unit,
    onCheckout: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Child: ${item.child.displayName}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Psychologist: ${item.psychologist.fullName}", style = MaterialTheme.typography.titleMedium)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Session Type: ${item.sessionType}", style = MaterialTheme.typography.bodyLarge)
        Text("Scheduled: ${item.scheduledStartTime} to ${item.scheduledEndTime}", style = MaterialTheme.typography.bodyLarge)
        Text("Price: ${item.price} ${item.currency}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier.weight(1f)
            ) {
                Text("Delete")
            }
            Button(
                onClick = { onCheckout(item.itemId) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Checkout")
            }
        }
    }
}