package com.example.kmd.presentation.screens.psychologist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.data.repository.AppointmentRepository
import com.example.kmd.domain.model.AvailableSlotsResponse
import com.example.kmd.domain.usecase.cart.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlotViewModel @Inject constructor(
    private val repository: AppointmentRepository,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _uiState: MutableState<AvailableSlotsResponse?> = mutableStateOf(null)
    val uiState: AvailableSlotsResponse? get() = _uiState.value

    private val _addToCartResult = MutableSharedFlow<Result<Unit>>()
    val addToCartResult = _addToCartResult.asSharedFlow()

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

    fun addToCart(
        childId: String,
        psychologistId: String,
        sessionType: String,
        slotId: Int
    ) {
        // --- START OF LOGGING ---
        Log.d("BookingFlow", "ViewModel: addToCart called with:")
        Log.d("BookingFlow", "  childId: $childId")
        Log.d("BookingFlow", "  psychologistId: $psychologistId")
        Log.d("BookingFlow", "  sessionType: $sessionType")
        Log.d("BookingFlow", "  slotId: $slotId")
        // --- END OF LOGGING ---

        viewModelScope.launch {
            val result = addToCartUseCase(
                childId = childId,
                psychologistId = psychologistId,
                sessionType = sessionType,
                slotId = slotId,
                notes = "" // Or get notes from the UI
            )

            // --- START OF LOGGING ---
            if (result.isSuccess) {
                Log.d("BookingFlow", "ViewModel: addToCartUseCase SUCCEEDED. Emitting success.")
                _addToCartResult.emit(Result.success(Unit))
            } else {
                Log.e("BookingFlow", "ViewModel: addToCartUseCase FAILED. Emitting failure.")
                Log.e("BookingFlow", "ViewModel: Exception: ${result.exceptionOrNull()?.stackTraceToString()}")
                _addToCartResult.emit(Result.failure(result.exceptionOrNull() ?: Exception("Unknown error")))
            }
            // --- END OF LOGGING ---
        }
    }
}