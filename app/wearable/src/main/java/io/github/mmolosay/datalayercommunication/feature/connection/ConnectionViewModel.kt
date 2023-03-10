package io.github.mmolosay.datalayercommunication.feature.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionFlowProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val handheldConnectionFlowProvider: Lazy<ConnectionFlowProvider?>,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(makeInitialUiState())
    val uiState = mutableUiState.asStateFlow()

    init {
        initialize()
    }

    private fun initialize() {
        observeHandheldConnectionState()
    }

    private fun observeHandheldConnectionState() {
        viewModelScope.launch {
            handheldConnectionFlowProvider.get()?.connectionFlow?.collect { isConnected ->
                mutableUiState.value =
                    if (isConnected) UiState.OK
                    else UiState.HandheldConnectionLost
            }
        }
    }

    private fun makeInitialUiState(): UiState =
        UiState.OK

    sealed interface UiState {
        object HandheldConnectionLost : UiState
        object OK : UiState
    }
}