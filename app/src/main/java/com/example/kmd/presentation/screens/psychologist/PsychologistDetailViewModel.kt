package com.example.kmd.presentation.screens.psychologist


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.kmd.domain.usecase.psychologist.GetPsychologistDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.kmd.domain.model.Child
import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.model.PsychologistDetail
import com.example.kmd.domain.usecase.parent.GetMyChildrenUseCase

@HiltViewModel
class PsychologistDetailViewModel @Inject constructor(
    private val getPsychologistDetailUseCase: GetPsychologistDetailUseCase,
    private val getMyChildrenUseCase: GetMyChildrenUseCase, // Inject the use case
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set

    var childrenState by mutableStateOf<List<Child>>(emptyList())
        private set

    init {
        val psychologistId = savedStateHandle.get<String>("psychologistId")
        if (psychologistId != null) {
            fetchPsychologistDetail(psychologistId)
            fetchChildren() // Fetch children
        } else {
            uiState = DetailUiState.Error("Missing user ID")
        }
    }

    private fun fetchChildren() {
        viewModelScope.launch {
            try {
                val result = getMyChildrenUseCase()
                if(result.isSuccess) {
                    childrenState = result.getOrNull()!!
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun fetchPsychologistDetail(userId: String) {
        viewModelScope.launch {
            uiState = DetailUiState.Loading
            try {
                val result = getPsychologistDetailUseCase(userId)
                uiState = DetailUiState.Success(result)
            } catch (e: Exception) {
                uiState = DetailUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val psychologist: PsychologistDetail) : DetailUiState()
        data class Error(val message: String) : DetailUiState()
    }
}