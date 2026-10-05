package com.example.mochicheck.ui

sealed interface MochiCheckAction {

    data class ToggleItem(
        val itemId: Int
    ) : MochiCheckAction

    data class MaxWeightChanged(
        val weightKg: Float
    ) : MochiCheckAction

    data object ClearSelection : MochiCheckAction
}

