//package com.example.kmd.presentation.screens.psychologist
//
//import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class PsychologistListViewModel @Inject constructor(
//    private val getPsychologistsUseCase: GetPsychologistsUseCase
//) : ViewModel() {
//
//    data class UiState(
//        val isLoading: Boolean = true,
//        val psychologists: List<Psychologist> = emptyList(),
//        val error: String? = null
//    )
//
//    private val _uiState = mutableStateOf(UiState())
//    val uiState: State<UiState> = _uiState
//
//    init {
//        fetchPsychologists()
//    }
//
//    private fun fetchPsychologists() {
//        viewModelScope.launch {
//            _uiState.value = UiState(isLoading = true)
//            try {
//                val result = getPsychologistsUseCase()
//                _uiState.value = UiState(isLoading = false, psychologists = result)
//            } catch (e: Exception) {
//                _uiState.value = UiState(isLoading = false, error = e.localizedMessage)
//            }
//        }
//    }
//}
