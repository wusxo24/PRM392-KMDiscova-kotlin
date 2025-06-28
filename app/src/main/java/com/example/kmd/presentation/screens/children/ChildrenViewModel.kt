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
    val errorMessage: String? = null
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
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    children = result.getOrNull() ?: emptyList()
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
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = addChildUseCase(child)
            if (result.isSuccess) {
                // This line is the key. It refetches the data after a successful add.
                loadMyChildren()
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to add child"
                )
            }
        }
    }
}