package io.github.mmolosay.datalayercommunication.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import io.github.mmolosay.datalayercommunication.destinations.AnimalsWithRequestsDestination
import io.github.mmolosay.datalayercommunication.startup.StartupViewModel.UiState

@RootNavGraph(start = true)
@Destination
@Composable
fun Startup(
    startupVM: StartupViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by startupVM.uiState.collectAsStateWithLifecycle()
    val onLoadingShown = remember {
        { startupVM.loadingShown() }
    }
    val onLoadingFinished = remember {
        { navController.navigate(AnimalsWithRequestsDestination) }
    }
    Startup(
        uiState = uiState,
        onLoadingShown = onLoadingShown,
        onLoadingFinished = onLoadingFinished,
    )
}

@Composable
fun Startup(
    uiState: UiState,
    onLoadingShown: () -> Unit,
    onLoadingFinished: () -> Unit,
) =
    when (uiState) {
        is UiState.Loading -> {
            StartupLoading()
            onLoadingShown()
        }
        is UiState.HandheldNotConnected -> StartupHandheldNotConnected()
        is UiState.Finished -> onLoadingFinished()
    }