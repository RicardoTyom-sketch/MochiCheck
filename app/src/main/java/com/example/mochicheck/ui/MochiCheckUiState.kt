package com.example.mochicheck.ui

import com.example.mochicheck.model.BackpackItem

data class MochiCheckUiState(
    val items: List<BackpackItem> = emptyList(),
    val maxWeightKg: Float = 5f,
    val totalWeightKg: Float = 0f,
    val progress: Float = 0f,
    val selectedCount: Int = 0,
    val heaviestItem: String = "",
    val loadLevel: LoadLevel = LoadLevel.EMPTY,
    val name: String = "",
    val matricula: String = "",
)