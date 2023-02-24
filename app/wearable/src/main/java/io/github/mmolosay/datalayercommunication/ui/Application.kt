package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import io.github.mmolosay.datalayercommunication.models.UiState

// region Preivews

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ApplicationPreview() {
    Application(
        uiState = previewUiState(),
        onGetAllAnimalsClick = {},
        onDeleteRandomCatClick = {},
        onClearOutputClick = {},
        onConnectionFailureTryAgainClick = {},
    )
}

private fun previewUiState(): UiState =
    UiState(
        showConnectionFailure = false,
        elapsedTime = "3569 ms",
        animals = emptyList(),
    )

// endregion

@Composable
fun Application(
    uiState: UiState,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearOutputClick: () -> Unit,
    onConnectionFailureTryAgainClick: () -> Unit,
) {
    val scalingLazyListState = rememberScalingLazyListState()
    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        timeText = { TimeText() }
    ) {
        if (uiState.showConnectionFailure)
            ConnectionFailure(
                onTryAgainClick = onConnectionFailureTryAgainClick,
            )
        else
            AnimalsWithRequests(
                uiState = uiState,
                scalingLazyListState = scalingLazyListState,
                onGetAllAnimalsClick = onGetAllAnimalsClick,
                onDeleteRandomCatClick = onDeleteRandomCatClick,
                onClearOutputClick = onClearOutputClick,
            )
    }
}
