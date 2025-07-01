package com.example.kmd.presentation.screens.cart

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.net.URLDecoder

@Composable
fun PaymentScreen(url: String) {
    val context = LocalContext.current
    val decodedUrl = URLDecoder.decode(url, "UTF-8")

    LaunchedEffect(decodedUrl) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(decodedUrl))
        context.startActivity(intent)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        // Optional: Text("Redirecting to payment...")
    }
}