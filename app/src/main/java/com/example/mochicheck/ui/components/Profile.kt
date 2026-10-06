package com.example.mochicheck.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Profile(
    name: String,
    matricula: String
) {
    Column {
        if (name.isNotEmpty()) {
            Text(text = "Nombre: $name")
        }

        if (matricula.isNotEmpty()) {
            Text(text = "Matricula: $matricula")
        }
    }
}