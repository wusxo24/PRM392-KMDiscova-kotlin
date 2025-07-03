package com.example.kmd.presentation.screens.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.BookingDetail
import com.example.kmd.domain.usecase.booking.GetBookingDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookingDetailUiState(
    val isLoading: Boolean = false,
    val bookingDetail: BookingDetail? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class BookingDetailViewModel @Inject constructor(
    private val getBookingDetailUseCase: GetBookingDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val appointmentId: String = savedStateHandle.get<String>("appointmentId")!!

    private val _uiState = MutableStateFlow(BookingDetailUiState())
    val uiState: StateFlow<BookingDetailUiState> = _uiState.asStateFlow()

    init {
        loadBookingDetail()
    }

    private fun loadBookingDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = getBookingDetailUseCase(appointmentId)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(isLoading = false, bookingDetail = result.getOrNull())
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message)
            }
        }
    }
}