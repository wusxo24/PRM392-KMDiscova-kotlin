package com.example.kmd.domain.usecase.chat

import com.example.kmd.domain.model.ChatResponse
import com.example.kmd.domain.model.SendMessageRequest
import com.example.kmd.domain.repository.IChatRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: IChatRepository
) {
    suspend operator fun invoke(request: SendMessageRequest): ChatResponse {
        // Send the parent's message
        val response = repository.sendMessage(request)
        
        // Generate auto-reply from psychologist
        val autoReply = generateAutoReply(request.content)
        if (autoReply != null) {
            // Create auto-reply message
            val autoReplyMessage = com.example.kmd.domain.model.ChatMessage(
                id = UUID.randomUUID().toString(),
                chatRoomId = request.chatRoomId,
                senderId = "psychologist",
                senderName = "Dr. Smith",
                senderType = com.example.kmd.domain.model.MessageSenderType.PSYCHOLOGIST,
                content = autoReply,
                timestamp = LocalDateTime.now(),
                isRead = false
            )
            
            // Store auto-reply message in repository
            if (repository is com.example.kmd.data.repository.ChatRepository) {
                repository.addAutoReplyMessage(request.chatRoomId, autoReplyMessage)
            }
            
            // Get all messages including the new auto-reply
            val allMessages = repository.getChatMessages(request.chatRoomId)
            
            return ChatResponse(
                message = response.message,
                chatRoom = response.chatRoom,
                messages = allMessages
            )
        }
        
        return response
    }
    
    private fun generateAutoReply(parentMessage: String): String? {
        val message = parentMessage.lowercase()
        
        return when {
            message.contains("hello") || message.contains("hi") -> {
                "Hello! I'm here to help you and your child. How can I assist you today?"
            }
            message.contains("appointment") || message.contains("booking") -> {
                "I'd be happy to help you with appointment scheduling. You can book a session through our booking system, or let me know if you have any specific questions about the process."
            }
            message.contains("child") && (message.contains("behavior") || message.contains("problem")) -> {
                "I understand you're concerned about your child's behavior. Every child is unique, and it's important to approach behavioral challenges with patience and understanding. Would you like to schedule a consultation to discuss this in more detail?"
            }
            message.contains("anxiety") || message.contains("stress") -> {
                "Anxiety and stress in children are common concerns. There are many effective strategies we can explore together. I'd recommend starting with a consultation to better understand your child's specific situation."
            }
            message.contains("thank") -> {
                "You're very welcome! I'm here to support you and your family. Don't hesitate to reach out if you have any more questions."
            }
            message.contains("help") -> {
                "I'm here to help! I can assist with behavioral concerns, emotional support, developmental questions, and more. What specific area would you like to discuss?"
            }
            message.contains("price") || message.contains("cost") || message.contains("fee") -> {
                "I offer various session types with different pricing. Initial consultations and regular sessions have different rates. You can view detailed pricing in my profile or contact our support team for more information."
            }
            message.contains("available") || message.contains("time") -> {
                "I have flexible scheduling options including weekday evenings and weekends. You can check my available slots through the booking system, or let me know your preferred times and I'll do my best to accommodate."
            }
            else -> {
                "Thank you for your message. I'm here to support you and your child. If you have specific concerns or questions, please feel free to share more details, or we can schedule a consultation to discuss things more thoroughly."
            }
        }
    }
} 