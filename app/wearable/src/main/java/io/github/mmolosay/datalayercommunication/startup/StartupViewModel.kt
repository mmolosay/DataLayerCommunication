package io.github.mmolosay.datalayercommunication.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.wearable.usecase.CheckIsHandheldDeviceConnectedUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val isHandheldDeviceConnected: CheckIsHandheldDeviceConnectedUseCase,
) : ViewModel() {

    private val fullUiState = MutableStateFlow(makeInitialUiState())
    val uiState = fullUiState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), fullUiState.value.toUiState())

    init {
        initialize()
    }

    /**
     * Notifies the ViewModel, that [UiState.Loading] was successfully consumed
     * and loading UI is shown now.
     */
    fun loadingShown() {
        fullUiState.update {
            it.copy(loadingShownTime = System.currentTimeMillis())
        }
    }

    private fun initialize() {
        executeCheckIsHandheldDeviceConnected()
    }

    private fun executeCheckIsHandheldDeviceConnected() {
        viewModelScope.launch {
            fullUiState.update {
                it.copy(
                    showHandheldNotConnected = !isHandheldDeviceConnected(),
                )
            }
            dismissLoading()
        }
    }

    private suspend fun dismissLoading() {
        fullUiState.update {
            // show loading for minimum some noticable time to prevent it from blinking
            if (it.showLoading && it.loadingShownTime != null) {
                val now = System.currentTimeMillis()
                val elapsed = (now - it.loadingShownTime).milliseconds
                val minimumScreenTime = 1_000.milliseconds
                val screenTimeLeft = minimumScreenTime - elapsed
                delay(screenTimeLeft) // skipped if <= 0
            }
            it.copy(showLoading = false)
        }
    }

    private fun makeInitialUiState(): FullUiState =
        FullUiState(
            loadingShownTime = null,
            showLoading = true,
            showHandheldNotConnected = false,
        )

    sealed interface UiState {
        object Loading : UiState
        object HandheldNotConnected : UiState
        object Finished : UiState
    }

    private data class FullUiState(
        val loadingShownTime: Long?,
        val showLoading: Boolean,
        val showHandheldNotConnected: Boolean,
    )

    private fun FullUiState.toUiState(): UiState =
        when {
            showLoading -> UiState.Loading
            showHandheldNotConnected -> UiState.HandheldNotConnected
            else -> UiState.Finished
        }
}