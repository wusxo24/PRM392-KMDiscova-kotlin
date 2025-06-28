package com.example.kmd.presentation.screens.children

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.Child

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChildDialog(
    onDismiss: () -> Unit,
    onConfirm: (Child) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Child") },
        text = {
            Column {
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth (YYYY-MM-DD)") })
                OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val child = Child(
                    id = "",
                    firstName = firstName,
                    lastName = lastName,
                    displayName = "$firstName $lastName",
                    dateOfBirth = dob,
                    age = 0, // Age is calculated on the backend
                    gender = gender
                )
                onConfirm(child)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}