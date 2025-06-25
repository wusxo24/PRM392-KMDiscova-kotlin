package com.example.kmd.presentation.screens.psychologist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.data.repository.AppointmentRepository
import com.example.kmd.domain.model.AvailableSlotsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _uiState: MutableState<AvailableSlotsResponse?> = mutableStateOf(null)
    val uiState: AvailableSlotsResponse? get() = _uiState.value

    fun loadSlots(userId: String, sessionType: String) {
        viewModelScope.launch {
            try {
                val slots = repository.getAvailableSlots(userId, sessionType)
                _uiState.value = slots
            } catch (e: Exception) {
                // Optionally handle or expose error here
                _uiState.value = null
            }
        }
    }
}
