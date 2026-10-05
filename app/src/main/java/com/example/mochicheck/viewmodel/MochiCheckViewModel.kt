package com.example.mochicheck.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mochicheck.model.BackpackItem
import com.example.mochicheck.ui.LoadLevel
import com.example.mochicheck.ui.MochiCheckAction
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

    fun onAction(action: MochiCheckAction) {
        when (action) {

            is MochiCheckAction.ToggleItem -> {
                toggleItem(action.itemId)
            }

            is MochiCheckAction.MaxWeightChanged -> {
                changeMaxWeight(action.weightKg)
            }

            MochiCheckAction.ClearSelection -> {
                clearSelection()
            }
        }
    }

    private fun toggleItem(itemId: Int) {
        val currentState = _uiState.value

        val updatedItems = currentState.items.map { item ->

            if (item.id == itemId) {
                item.copy(
                    isSelected = !item.isSelected
                )
            } else {
                item
            }
        }

        _uiState.value = calculateState(
            items = updatedItems,
            maxWeightKg = currentState.maxWeightKg
        )
    }

    private fun changeMaxWeight(weightKg: Float) {
        val currentState = _uiState.value

        _uiState.value = calculateState(
            items = currentState.items,
            maxWeightKg = weightKg
        )
    }

    private fun clearSelection() {
        val currentState = _uiState.value

        val clearedItems = currentState.items.map { item ->
            item.copy(
                isSelected = false
            )
        }

        _uiState.value = calculateState(
            items = clearedItems,
            maxWeightKg = currentState.maxWeightKg
        )
    }

    private fun calculateState(
        items: List<BackpackItem>,
        maxWeightKg: Float
    ): MochiCheckUiState {

        val selectedItems =
            items.filter { item ->
                item.isSelected
            }

        val totalWeight =
            selectedItems
                .sumOf { item ->
                    item.weightKg.toDouble()
                }
                .toFloat()

        val ratio =
            if (maxWeightKg > 0f) {
                totalWeight / maxWeightKg
            } else {
                0f
            }

        val progress =
            ratio.coerceIn(0f, 1f)

        val heaviestItem =
            selectedItems
                .maxByOrNull { item ->
                    item.weightKg
                }
                ?.name ?: ""

        val loadLevel = when {

            selectedItems.isEmpty() ->
                LoadLevel.EMPTY

            ratio <= 0.5f ->
                LoadLevel.LIGHT

            ratio <= 0.8f ->
                LoadLevel.MODERATE

            ratio <= 1f ->
                LoadLevel.HEAVY

            else ->
                LoadLevel.OVER_LIMIT
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