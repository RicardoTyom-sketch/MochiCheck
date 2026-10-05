package com.example.mochicheck.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mochicheck.model.BackpackItem

@Composable
fun BackpackItemCard(
    item: BackpackItem,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${item.name} - ${item.weightKg} kg",
                modifier = Modifier.weight(1f)
            )

            Checkbox(
                checked = item.isSelected,
                onCheckedChange = {
                    onCheckedChange()
                }
            )
        }
    }
}

