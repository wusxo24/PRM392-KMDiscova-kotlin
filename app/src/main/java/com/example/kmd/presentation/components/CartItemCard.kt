package com.example.kmd.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.CartItem

@Composable
fun CartItemCard(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.productName, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Price: $${item.price}", style = MaterialTheme.typography.bodyMedium)
            }
            QuantitySelector(
                quantity = item.quantity,
                onIncrease = { onQuantityChange(item.quantity + 1) },
                onDecrease = { onQuantityChange(item.quantity - 1) }
            )
            IconButton(onClick = onRemove) {
                androidx.compose.material3.Icon(Icons.Default.Delete, contentDescription = "Remove item")
            }
        }
    }
}
