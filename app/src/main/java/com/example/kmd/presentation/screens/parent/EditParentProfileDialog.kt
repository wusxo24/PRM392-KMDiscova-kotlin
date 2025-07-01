package com.example.kmd.presentation.screens.parent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.ParentProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditParentProfileDialog(
    profile: ParentProfile,
    onDismiss: () -> Unit,
    onSave: (ParentProfile) -> Unit
) {
    var firstName by remember { mutableStateOf(profile.firstName) }
    var lastName by remember { mutableStateOf(profile.lastName) }
    var phoneNumber by remember { mutableStateOf(profile.phoneNumber) }
    var address by remember { mutableStateOf(profile.addressLine1) }
    var city by remember { mutableStateOf(profile.city) }
    var state by remember { mutableStateOf(profile.stateProvince) }
    var postalCode by remember { mutableStateOf(profile.postalCode) }
    var country by remember { mutableStateOf(profile.country) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                OutlinedTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
                OutlinedTextField(value = state, onValueChange = { state = it }, label = { Text("State") })
                OutlinedTextField(value = postalCode, onValueChange = { postalCode = it }, label = { Text("Postal Code") })
                OutlinedTextField(value = country, onValueChange = { country = it }, label = { Text("Country") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedProfile = profile.copy(
                    firstName = firstName,
                    lastName = lastName,
                    fullName = "$firstName $lastName",
                    phoneNumber = phoneNumber,
                    addressLine1 = address,
                    city = city,
                    stateProvince = state,
                    postalCode = postalCode,
                    country = country
                )
                onSave(updatedProfile)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}