package com.example.kmd.presentation.screens.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.usecase.parent.GetParentProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParentProfileUiState(
    val isLoading: Boolean = false,
    val parentProfile: ParentProfile? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ParentProfileViewModel @Inject constructor(
    private val getParentProfileUseCase: GetParentProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParentProfileUiState())
    val uiState: StateFlow<ParentProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = getParentProfileUseCase()
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(isLoading = false, parentProfile = result.getOrNull())
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message ?: "Failed to load profile")
            }
        }
    }
}