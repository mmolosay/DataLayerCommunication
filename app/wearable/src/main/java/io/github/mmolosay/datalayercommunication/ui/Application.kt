package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.wear.rememberWearNavHostEngine
import io.github.mmolosay.datalayercommunication.NavGraphs
import io.github.mmolosay.datalayercommunication.animals.AnimalsWithRequests
import io.github.mmolosay.datalayercommunication.destinations.AnimalsWithRequestsDestination
import io.github.mmolosay.datalayercommunication.destinations.StartupDestination

// region Preivews

@Preview
@Composable
fun ApplicationPreview() {
    Application()
}

// endregion

@Composable
fun Application() {
    val navHostEngine = rememberWearNavHostEngine()
    val navController = navHostEngine.rememberNavController()

    val scalingLazyListState = rememberScalingLazyListState()

    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        timeText = { TimeText() },
    ) {
        DestinationsNavHost(
            engine = navHostEngine,
            navController = navController,
            navGraph = NavGraphs.root,
            startRoute = StartupDestination,
        ) {
            composable(AnimalsWithRequestsDestination) {
                AnimalsWithRequests(
                    navController = navController,
                    scalingLazyListState = scalingLazyListState,
                )
            }
        }
    }
}
