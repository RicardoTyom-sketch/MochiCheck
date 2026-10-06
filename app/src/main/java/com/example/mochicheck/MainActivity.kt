package com.example.mochicheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mochicheck.ui.MochiCheckAction
import com.example.mochicheck.ui.MochiCheckScreen
import com.example.mochicheck.ui.theme.MochiCheckTheme
import com.example.mochicheck.viewmodel.MochiCheckViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MochiCheckViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MochiCheckTheme {

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    MochiCheckScreen(
                        uiState = uiState,

                        onItemSelected = { itemId ->
                            viewModel.onAction(
                                MochiCheckAction.ToggleItem(itemId)

                            )
                        },
                        onShowProfile = {
                            viewModel.showProfile() //ultima modificacion

                        },

                        onMaxWeightChanged = { weight ->
                            viewModel.onAction(
                                MochiCheckAction.MaxWeightChanged(weight)
                            )
                        },

                        onClear = {
                            viewModel.onAction(
                                MochiCheckAction.ClearSelection
                            )
                        },

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}