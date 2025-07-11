package com.example.kmd.presentation.screens.cart

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult

@Composable
fun PaymentScreen(
    orderId: String,
    viewModel: CartDetailViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {},
    onFailure: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    var clientSecret by remember { mutableStateOf<String?>(null) }
    var ephemeralKey by remember { mutableStateOf<String?>(null) }
    var customerId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(orderId) {
        viewModel.initiatePayment(
            orderId = orderId,
            onSuccess = { secret ->
                clientSecret = secret
                // You might need to get these from your backend as well
                // ephemeralKey = ephemeralKeyFromBackend
                // customerId = customerIdFromBackend
            },
            onFailure = { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                onFailure(message)
            }
        )
    }

    clientSecret?.let { secret ->
        PaymentScreenContent(
            clientSecret = secret,
            ephemeralKey = ephemeralKey,
            customerId = customerId,
            onBack = onBack,
            onSuccess = {
                Toast.makeText(context, "Payment success!", Toast.LENGTH_SHORT).show()
                onSuccess()
            },
            onFailure = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                onFailure(errorMessage)
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreenContent(
    clientSecret: String,
    ephemeralKey: String? = null,
    customerId: String? = null,
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val context = LocalContext.current

    // Initialize PaymentConfiguration (do this once in your Application class ideally)
    LaunchedEffect(Unit) {
        PaymentConfiguration.init(
            context = context,
            publishableKey = "pk_test_51RW4q4Rq8N8jdwzZXus9YjEnUhdkk3TZIll62vHWM7CBwRaqIRnmjPDKXWx1ytsJ6RrHurL77M4yo0uMjMXVdZV400DQhwWn35"
        )
    }

    // Configure PaymentSheet
    val configuration = PaymentSheet.Configuration(
        merchantDisplayName = "Your Store Name",
        // If you have customer info, you can enable saved payment methods
        customer = if (customerId != null && ephemeralKey != null) {
            PaymentSheet.CustomerConfiguration(
                id = customerId,
                ephemeralKeySecret = ephemeralKey
            )
        } else null
    )

    // Payment Sheet launcher
    val paymentSheetLauncher = rememberLauncherForActivityResult(
        contract = PaymentSheetContract()
    ) { result ->
        when (result) {
            is PaymentSheetResult.Completed -> {
                onSuccess()
            }
            is PaymentSheetResult.Canceled -> {
                onFailure("Payment was canceled")
            }
            is PaymentSheetResult.Failed -> {
                onFailure("Payment failed: ${result.error.localizedMessage}")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar with back button
        TopAppBar(
            title = { Text("Payment") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        // Payment content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Payment Information",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Tap the button below to open the secure payment form",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Button(
                onClick = {
                    // Correct way to launch PaymentSheet
                    val args = PaymentSheetContract.Args.createPaymentIntentArgs(
                        clientSecret = clientSecret,
                        config = configuration
                    )
                    paymentSheetLauncher.launch(args)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hoàn tất thanh toán")
            }
        }
    }
}