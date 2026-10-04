package com.example.mochicheck.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mochicheck.model.BackpackItem
import com.example.mochicheck.ui.LoadLevel
import com.example.mochicheck.ui.MochiCheckUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MochiCheckViewModel : ViewModel() {

    private val initialItems = listOf(
        BackpackItem(1, "Laptop", 1.8f),
        BackpackItem(2, "Botella", 0.7f),
        BackpackItem(3, "Libro", 0.6f),
        BackpackItem(4, "Cuaderno", 0.4f),
        BackpackItem(5, "Cargador", 0.3f),
        BackpackItem(6, "Audífonos", 0.2f)
    )

    private val _uiState = MutableStateFlow(
        calculateState(
            items = initialItems,
            maxWeightKg = 5f
        )
    )

    val uiState: StateFlow<MochiCheckUiState> =
        _uiState.asStateFlow()

    private fun calculateState(
        items: List<BackpackItem>,
        maxWeightKg: Float
    ): MochiCheckUiState {

        val selectedItems = items.filter { it.isSelected }

        val totalWeight = selectedItems
            .sumOf { it.weightKg.toDouble() }
            .toFloat()

        val ratio = if (maxWeightKg > 0f) {
            totalWeight / maxWeightKg
        } else {
            0f
        }

        val progress = ratio.coerceIn(0f, 1f)

        val heaviestItem =
            selectedItems.maxByOrNull { it.weightKg }?.name ?: ""

        val loadLevel = when {
            selectedItems.isEmpty() -> LoadLevel.EMPTY
            ratio <= 0.5f -> LoadLevel.LIGHT
            ratio <= 0.8f -> LoadLevel.MODERATE
            ratio <= 1f -> LoadLevel.HEAVY
            else -> LoadLevel.OVER_LIMIT
        }

        return MochiCheckUiState(
            items = items,
            maxWeightKg = maxWeightKg,
            totalWeightKg = totalWeight,
            progress = progress,
            selectedCount = selectedItems.size,
            heaviestItem = heaviestItem,
            loadLevel = loadLevel
        )
    }
}