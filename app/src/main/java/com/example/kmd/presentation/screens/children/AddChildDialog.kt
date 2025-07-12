package com.example.kmd.presentation.screens.children

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmd.domain.model.Child
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChildDialog(
    onDismiss: () -> Unit,
    onConfirm: (Child) -> Unit,
    isLoading: Boolean = false
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var healthStatus by remember { mutableStateOf("") }
    var developmentalConcerns by remember { mutableStateOf("") }
    var parentalGoals by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Child") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = firstName, 
                    onValueChange = { firstName = it }, 
                    label = { Text("First Name *") },
                    supportingText = { 
                        if (firstName.isEmpty()) {
                            Text("Please enter the child's first name", color = MaterialTheme.colorScheme.error)
                        } else {
                            Text("")
                        }
                    },
                    isError = firstName.isEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lastName, 
                    onValueChange = { lastName = it }, 
                    label = { Text("Last Name (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dob, 
                    onValueChange = { dob = it }, 
                    label = { Text("Date of Birth (YYYY-MM-DD) *") },
                    supportingText = { 
                        if (dob.isEmpty()) {
                            Text("Please enter the child's date of birth", color = MaterialTheme.colorScheme.error)
                        } else {
                            val errorMsg = getDateErrorMessage(dob)
                            if (errorMsg != null) {
                                Text(errorMsg, color = MaterialTheme.colorScheme.error)
                            } else {
                                val age = try {
                                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                    val birthDate = LocalDate.parse(dob, formatter)
                                    val today = LocalDate.now()
                                    today.year - birthDate.year - (if (today.dayOfYear < birthDate.dayOfYear) 1 else 0)
                                } catch (e: Exception) { 0 }
                                Text("Age: $age years old", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    },
                    isError = dob.isEmpty() || (dob.isNotEmpty() && !isDateValid(dob)),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = gender, 
                    onValueChange = { gender = it }, 
                    label = { Text("Gender *") },
                    supportingText = { 
                        if (gender.isEmpty()) {
                            Text("Please enter the child's gender", color = MaterialTheme.colorScheme.error)
                        } else {
                            Text("")
                        }
                    },
                    isError = gender.isEmpty(),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = healthStatus, 
                    onValueChange = { healthStatus = it }, 
                    label = { Text("Health Status (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = developmentalConcerns, 
                    onValueChange = { developmentalConcerns = it }, 
                    label = { Text("Developmental Concerns (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = parentalGoals, 
                    onValueChange = { parentalGoals = it }, 
                    label = { Text("Parental Goals (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                val child = Child(
                        id = "temp-${System.currentTimeMillis()}",
                    firstName = firstName,
                        lastName = lastName.ifEmpty { "" },
                        displayName = if (lastName.isNotEmpty()) "$firstName $lastName" else firstName,
                    dateOfBirth = dob,
                    age = 0, // Age is calculated on the backend
                        gender = gender.ifEmpty { "" }
                )
                onConfirm(child)
                },
                enabled = !isLoading && firstName.isNotEmpty() && dob.isNotEmpty() && isDateValid(dob) && gender.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                Text("Add")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun isDateValid(dateString: String): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateString, formatter)
        val today = LocalDate.now()
        
        // Check if birth date is in the future
        if (birthDate.isAfter(today)) {
            return false
        }
        
        val age = today.year - birthDate.year - (if (today.dayOfYear < birthDate.dayOfYear) 1 else 0)
        age >= 5
    } catch (e: DateTimeParseException) {
        false
    }
}

private fun getDateErrorMessage(dateString: String): String? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateString, formatter)
        val today = LocalDate.now()
        
        if (birthDate.isAfter(today)) {
            return "Date of birth cannot be in the future"
        }
        
        val age = today.year - birthDate.year - (if (today.dayOfYear < birthDate.dayOfYear) 1 else 0)
        if (age < 5) {
            return "Child must be at least 5 years old (currently $age years old)"
        }
        
        null
    } catch (e: DateTimeParseException) {
        "Please use format YYYY-MM-DD (e.g., 2018-01-15)"
    }
}