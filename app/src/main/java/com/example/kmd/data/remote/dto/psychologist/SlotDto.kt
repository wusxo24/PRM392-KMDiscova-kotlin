package com.example.kmd.data.remote.dto.psychologist

data class AvailableSlotsResponseDto(
    val psychologist_name: String,
    val session_type: String,
    val total_slots: Int,
    val available_slots: List<SlotDto>
)

data class SlotDto(
    val slot_id: Int,
    val date: String,
    val start_time: String,
    val end_time: String,
    val session_types: List<String>,
    val is_consecutive_block: Boolean
)
