package com.example.kmd.presentation.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.ChatRoom
import com.example.kmd.domain.usecase.chat.GetChatRoomsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatRoomsUseCase: GetChatRoomsUseCase
) : ViewModel() {

    var uiState by mutableStateOf<ChatListUiState>(ChatListUiState.Loading)
        private set

    fun loadChatRooms() {
        viewModelScope.launch {
            uiState = ChatListUiState.Loading
            try {
                val chatRooms = getChatRoomsUseCase()
                uiState = ChatListUiState.Success(chatRooms)
            } catch (e: Exception) {
                uiState = ChatListUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    sealed class ChatListUiState {
        object Loading : ChatListUiState()
        data class Success(val chatRooms: List<ChatRoom>) : ChatListUiState()
        data class Error(val message: String) : ChatListUiState()
    }
} 