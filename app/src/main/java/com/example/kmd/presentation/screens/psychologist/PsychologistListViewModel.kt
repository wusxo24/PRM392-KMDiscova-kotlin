package com.example.kmd.presentation.screens.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.usecase.psychologist.GetPsychologistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsychologistListViewModel @Inject constructor(
    private val getPsychologistsUseCase: GetPsychologistsUseCase
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val psychologists: List<Psychologist> = emptyList(),
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchPsychologists()
    }

    private fun fetchPsychologists() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            try {
                val result = getPsychologistsUseCase()
                _uiState.value = UiState(psychologists = result)
            } catch (e: Exception) {
                _uiState.value = UiState(error = e.message ?: "An error occurred")
            }
        }
    }
}
