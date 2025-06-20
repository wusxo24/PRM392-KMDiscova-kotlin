package com.example.kmd.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.CartItem

@Composable
fun CartSummary(cartItems: List<CartItem>, modifier: Modifier = Modifier) {
    val totalCount = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.price * it.quantity }
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Total items: $totalCount", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Total price: $${"%.2f".format(totalPrice)}", style = MaterialTheme.typography.bodyLarge)
    }
}
