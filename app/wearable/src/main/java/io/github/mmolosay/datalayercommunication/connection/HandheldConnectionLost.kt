package io.github.mmolosay.datalayercommunication.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text
import com.ramcosta.composedestinations.annotation.Destination
import io.github.mmolosay.datalayercommunication.ui.navigation.StartedNavGraph

// region Previews

@Preview
@Composable
private fun HandheldConnectionLostPreview() =
    HandheldConnectionLost()

// endregion

@StartedNavGraph
@Destination
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
