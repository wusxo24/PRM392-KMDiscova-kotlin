package com.example.kmd.presentation.screens.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.usecase.parent.CreateParentProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateParentProfileUiState(
    val isLoading: Boolean = false,
    val parentProfile: ParentProfile? = null,
    val errorMessage: String? = null,
    val isProfileCreated: Boolean = false
)

@HiltViewModel
class CreateParentProfileViewModel @Inject constructor(
    private val createParentProfileUseCase: CreateParentProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateParentProfileUiState())
    val uiState: StateFlow<CreateParentProfileUiState> = _uiState.asStateFlow()

    fun createProfile(profile: ParentProfile) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = createParentProfileUseCase(profile)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    parentProfile = result.getOrNull(),
                    isProfileCreated = true
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to create profile"
                )
            }
        }
    }
}