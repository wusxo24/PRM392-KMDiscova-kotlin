package com.example.kmd.presentation.screens.psychologist

import android.util.Log
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
        Log.d("ViewModel", "init called") // <--- Step 1
        fetchPsychologists()
    }
    private fun fetchPsychologists() {
        viewModelScope.launch {
            Log.d("ViewModel", "Launching coroutine") // <--- Step 2
            _uiState.value = UiState(isLoading = true)
            try {
                val result = getPsychologistsUseCase()
                Log.d("ViewModel", "Fetched ${result.size} psychologists") // <--- Step 3
                _uiState.value = UiState(psychologists = result)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching psychologists", e) // <--- Step 4
                _uiState.value = UiState(error = e.message ?: "An error occurred")
            }
        }
    }
}
