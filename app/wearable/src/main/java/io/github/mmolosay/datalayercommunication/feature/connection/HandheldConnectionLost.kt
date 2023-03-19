package io.github.mmolosay.datalayercommunication.feature.connection

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

// region Previews

@Preview
@Composable
private fun OverlayPreview() =
    HandheldConnectionLostOverlay()

@Preview
@Composable
private fun Preview() =
    HandheldConnectionLost()

// endregion

@Composable
fun HandheldConnectionLostOverlay(
    modifier: Modifier = Modifier,
) {
    BackHandler { /* ignore back press */ }
    HandheldConnectionLost(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    )
}


/*
 * It is not a destination, because we don't want user to be able to navigate back from here.
 */
@Composable
fun HandheldConnectionLost(
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Watch is not connected to handheld device.\nReconnection will be detected automatically.",
            textAlign = TextAlign.Center,
        )
    }

@Composable
@SuppressLint("ComposableNaming")
fun observeHandheldConnectionState(
    connectionVM: ConnectionViewModel,
    onChanged: (isConnectionLost: Boolean) -> Unit,
) {
    val uiState by connectionVM.uiState.collectAsStateWithLifecycle()
    val isConnectionLost = uiState.isHandheldConnectionLost
    onChanged(isConnectionLost)
}
