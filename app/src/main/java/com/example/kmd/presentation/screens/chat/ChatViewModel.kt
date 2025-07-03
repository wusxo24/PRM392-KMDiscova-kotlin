package com.example.kmd.presentation.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.ChatMessage
import com.example.kmd.domain.model.SendMessageRequest
import com.example.kmd.domain.usecase.chat.GetChatMessagesUseCase
import com.example.kmd.domain.usecase.chat.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    var uiState by mutableStateOf(ChatUiState())
        private set

    fun loadMessages(chatRoomId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val messages = getChatMessagesUseCase(chatRoomId)
                uiState = uiState.copy(
                    isLoading = false,
                    messages = messages,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    fun sendMessage(chatRoomId: String, content: String) {
        if (content.isBlank()) return
        
        viewModelScope.launch {
            uiState = uiState.copy(isSending = true)
            try {
                val request = SendMessageRequest(
                    chatRoomId = chatRoomId,
                    content = content
                )
                val response = sendMessageUseCase(request)
                
                // Update messages with the response (includes auto-reply)
                val updatedMessages = response.messages ?: emptyList()
                uiState = uiState.copy(
                    isSending = false,
                    messages = updatedMessages
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isSending = false,
                    error = e.localizedMessage ?: "Failed to send message"
                )
            }
        }
    }

    data class ChatUiState(
        val messages: List<ChatMessage> = emptyList(),
        val isLoading: Boolean = false,
        val isSending: Boolean = false,
        val error: String? = null
    )
} 