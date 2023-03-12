package io.github.mmolosay.datalayercommunication.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import io.github.mmolosay.datalayercommunication.NavGraphs
import io.github.mmolosay.datalayercommunication.destinations.StartupDestination
import io.github.mmolosay.datalayercommunication.startup.StartupViewModel.UiState

@RootNavGraph(start = true)
@Destination
@Composable
fun Startup(
    startupVM: StartupViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val uiState by startupVM.uiState.collectAsStateWithLifecycle()
    val onLoadingShown = remember {
        { startupVM.loadingShown() }
    }
    val onFinished = remember {
        {
            navigator.navigate(NavGraphs.mainApp) {
                // clear back stack to prevent user from navigating back to startup stage
                popUpTo(StartupDestination) { inclusive = true }
            }
        }
    }
    Startup(
        uiState = uiState,
        onLoadingShown = onLoadingShown,
        onFinished = onFinished,
    )
}

@Composable
fun Startup(
    uiState: UiState,
    onLoadingShown: () -> Unit,
    onFinished: () -> Unit,
) =
    when (uiState) {
        is UiState.Loading -> {
            StartupLoading()
            onLoadingShown()
        }
        is UiState.HandheldNotConnected -> StartupHandheldNotConnected()
        is UiState.Finished -> onFinished()
    }