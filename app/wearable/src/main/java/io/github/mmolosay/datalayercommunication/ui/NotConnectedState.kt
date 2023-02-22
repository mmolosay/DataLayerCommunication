package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.foundation.layout.Box
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
private fun NotConnectedStatePreview() =
    NotConnectedState()

// endregion

@Composable
fun NotConnectedState() =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Watch is not connected to handheld device.",
            textAlign = TextAlign.Center,
        )
    }
