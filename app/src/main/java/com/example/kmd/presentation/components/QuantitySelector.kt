package com.example.kmd.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(4.dp)) {
        Button(onClick = onDecrease, enabled = quantity > 1) {
            Text("-")
        }
        Text(text = quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
        Button(onClick = onIncrease) {
            Text("+")
        }
    }
}
