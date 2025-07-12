package com.example.kmd.presentation.screens.children

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.Child
import com.example.kmd.domain.usecase.parent.AddChildUseCase
import com.example.kmd.domain.usecase.parent.GetMyChildrenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChildrenUiState(
    val isLoading: Boolean = false,
    val children: List<Child> = emptyList(),
    val errorMessage: String? = null,
    val isAdding: Boolean = false,
    val successMessage: String? = null
)

@HiltViewModel
class ChildrenViewModel @Inject constructor(
    private val getMyChildrenUseCase: GetMyChildrenUseCase,
    private val addChildUseCase: AddChildUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChildrenUiState())
    val uiState: StateFlow<ChildrenUiState> = _uiState.asStateFlow()

    init {
        loadMyChildren()
    }

    fun loadMyChildren() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = getMyChildrenUseCase()
            if (result.isSuccess) {
                val children = result.getOrNull() ?: emptyList()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    children = children
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to load children"
                )
            }
        }
    }

    fun addChild(child: Child) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAdding = true, errorMessage = null, successMessage = null)
            val result = addChildUseCase(child)
            if (result.isSuccess) {
                // Reset adding state
                _uiState.value = _uiState.value.copy(isAdding = false)
                
                // Add the new child to the current list immediately for instant UI feedback
                val newChild = result.getOrNull()
                if (newChild != null) {
                    val currentChildren = _uiState.value.children.toMutableList()
                    currentChildren.add(newChild)
                    _uiState.value = _uiState.value.copy(
                        children = currentChildren,
                        successMessage = "${child.firstName} has been added successfully!"
                    )
                }
                
                // Then refresh from server to ensure we have the latest data
                loadMyChildren()
            } else {
                _uiState.value = _uiState.value.copy(
                    isAdding = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to add child"
                )
            }
        }
    }
}