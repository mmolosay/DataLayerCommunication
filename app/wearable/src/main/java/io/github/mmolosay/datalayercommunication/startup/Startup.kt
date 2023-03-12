package io.github.mmolosay.datalayercommunication.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    vm: StartupViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    Startup(
        uiState = uiState,
        onLoadingShown = { vm.loadingShown() },
        onLoadingFinished = { navController.navigate(AnimalsWithRequestsDestination) }, // TODO: check recomposition after remembering this lambda
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