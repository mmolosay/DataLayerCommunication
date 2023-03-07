package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text

// region Previews

@Preview
@Composable
private fun HandheldConnectionLostPreview() =
    HandheldConnectionLost()

// endregion

@Composable
fun HandheldConnectionLost() =
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Watch is not connected to handheld device.\nReconnection will be detected automatically.",
            textAlign = TextAlign.Center,
        )
    }
