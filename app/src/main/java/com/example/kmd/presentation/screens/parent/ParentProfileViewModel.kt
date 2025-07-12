package com.example.kmd.presentation.screens.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.ParentProfile
import com.example.kmd.domain.usecase.parent.GetParentProfileUseCase
import com.example.kmd.domain.usecase.parent.UpdateParentProfileUseCase
import com.example.kmd.di.notification.NotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParentProfileUiState(
    val isLoading: Boolean = false,
    val parentProfile: ParentProfile? = null,
    val errorMessage: String? = null,
    val isEditing: Boolean = false,
    val isUpdating: Boolean = false
)

@HiltViewModel
class ParentProfileViewModel @Inject constructor(
    private val getParentProfileUseCase: GetParentProfileUseCase,
    private val updateParentProfileUseCase: UpdateParentProfileUseCase,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParentProfileUiState())
    val uiState: StateFlow<ParentProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
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

    fun onEditClick() {
        _uiState.value = _uiState.value.copy(isEditing = true)
    }

    fun onDismissEditDialog() {
        _uiState.value = _uiState.value.copy(isEditing = false)
    }

    fun onSaveProfile(editedProfile: ParentProfile) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdating = true, errorMessage = null)
            val result = updateParentProfileUseCase(editedProfile)
            if(result.isSuccess){
                // Update the profile in UI state immediately
                _uiState.value = _uiState.value.copy(
                    isUpdating = false,
                    isEditing = false,
                    parentProfile = result.getOrNull() ?: editedProfile
                )
                // Also reload the profile to ensure we have the latest data
                loadProfile()
            } else {
                _uiState.value = _uiState.value.copy(
                    isUpdating = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to update profile"
                )
            }
        }
    }

    fun testNotification() {
        viewModelScope.launch {
            notificationManager.showCartNotification(3)
        }
    }
}