package io.github.mmolosay.datalayercommunication.startup

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
private fun StartupHandheldNotConnectedPreview() =
    StartupHandheldNotConnected()

// endregion

@Composable
fun StartupHandheldNotConnected() =
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Watch is not connected to handheld device.\nPlease, connect handheld device and restart an app.", // TODO: detect connection automatically
            textAlign = TextAlign.Center,
        )
    }
