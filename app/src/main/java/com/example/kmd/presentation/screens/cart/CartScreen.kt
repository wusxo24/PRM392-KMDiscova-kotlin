package com.example.kmd.presentation.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.CartItem
import com.example.kmd.presentation.components.CartBadge
import com.example.kmd.presentation.components.CartSummary
import com.example.kmd.presentation.components.CartItemCard
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CartBadge(cartItems = viewModel.cartItems)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your Cart", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onQuantityChange = { newQty ->
                        if (newQty > 0) viewModel.updateCartItem(item.copy(quantity = newQty))
                    },
                    onRemove = { viewModel.removeFromCart(item) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CartSummary(cartItems = cartItems)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.clearCart() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Cart")
        }
    }
}
