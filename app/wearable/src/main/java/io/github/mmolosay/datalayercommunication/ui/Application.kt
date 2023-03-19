package io.github.mmolosay.datalayercommunication.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.wear.rememberWearNavHostEngine
import io.github.mmolosay.datalayercommunication.feature.NavGraphs
import io.github.mmolosay.datalayercommunication.feature.connection.ConnectionViewModel

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
            navGraph = NavGraphs.root,
            engine = navHostEngine,
            navController = navController,
            dependenciesContainerBuilder = {
                buildDependenciesContainer(
                    scalingLazyListState = scalingLazyListState,
                )
            },
        )
    }
}

@Composable
@SuppressLint("ComposableNaming")
private fun DependenciesContainerBuilder<*>.buildDependenciesContainer(
    scalingLazyListState: ScalingLazyListState,
) {
    dependency(scalingLazyListState)

    // provide same instance of ConnectionViewModel to all destinations of StartedNavGraph
    dependency(NavGraphs.mainApp) {
        // we are not using a navBackStackEntry here
        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(NavGraphs.mainApp.route)
        }
        hiltViewModel<ConnectionViewModel>(parentEntry)
    }
}
