package com.example.mochicheck.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mochicheck.ui.components.BackpackItemCard

@Composable
fun MochiCheckScreen(
    uiState: MochiCheckUiState,
    onItemSelected: (Int) -> Unit,
    onMaxWeightChanged: (Float) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(text = "MochiCheck")
            Text(text = "Selecciona los objetos que llevas en tu mochila")
        }

        items(
            items = uiState.items,
            key = { item -> item.id }
        ) { item ->

            BackpackItemCard(
                item = item,
                onCheckedChange = {
                    onItemSelected(item.id)
                }
            )
        }

        item {
            Column {
                Text(
                    text = "Peso máximo: ${uiState.maxWeightKg} kg"
                )

                Slider(
                    value = uiState.maxWeightKg,
                    onValueChange = onMaxWeightChanged,
                    valueRange = 2f..10f
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Peso actual: ${uiState.totalWeightKg} kg"
                    )

                    LinearProgressIndicator(
                        progress = { uiState.progress },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Objetos seleccionados: ${uiState.selectedCount}"
                    )

                    if (uiState.heaviestItem.isNotEmpty()) {
                        Text(
                            text = "Objeto más pesado: ${uiState.heaviestItem}"
                        )
                    }

                    Text(
                        text = "Estado: ${uiState.loadLevel}"
                    )
                }
            }
        }

        item {
            Button(
                onClick = onClear,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Limpiar mochila")
            }
        }
    }
}