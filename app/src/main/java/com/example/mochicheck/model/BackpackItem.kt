package com.example.mochicheck.model

data class BackpackItem(
    val id: Int,
    val name: String,
    val weightKg: Float,
    val isSelected: Boolean = false
)
