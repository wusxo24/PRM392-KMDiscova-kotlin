//package com.example.kmd.presentation.screens.children
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.kmd.domain.model.Child
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyChildrenScreen(
//    onAddChild: () -> Unit,
//    viewModel: ChildrenViewModel = hiltViewModel()
//) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(onClick = onAddChild) {
//                Icon(Icons.Default.Add, contentDescription = "Add Child")
//            }
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//        ) {
//            if (uiState.isLoading) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//            } else if (uiState.children.isEmpty()) {
//                Text("No children found.", modifier = Modifier.align(Alignment.CenterHorizontally))
//            } else {
//                LazyColumn {
//                    items(uiState.children) { child ->
//                        ChildItem(child)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ChildItem(child: Child) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = child.displayName, style = MaterialTheme.typography.headlineSmall)
//            Text(text = "Age: ${child.age}", style = MaterialTheme.typography.bodyMedium)
//            Text(text = "Gender: ${child.gender}", style = MaterialTheme.typography.bodyMedium)
//        }
//    }
//}