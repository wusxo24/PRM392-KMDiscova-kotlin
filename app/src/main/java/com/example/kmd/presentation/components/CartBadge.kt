package com.example.kmd.presentation.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.kmd.domain.model.CartItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CartBadge(cartItems: StateFlow<List<CartItem>>, modifier: Modifier = Modifier) {
    val items by cartItems.collectAsState()
    val totalCount = items.sumOf { it.quantity }
    BadgedBox(
        badge = {
            if (totalCount > 0) Badge { androidx.compose.material3.Text(totalCount.toString()) }
        },
        modifier = modifier
    ) {
        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
    }
}
