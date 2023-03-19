package io.github.mmolosay.datalayercommunication.feature.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionFlowProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                mutableUiState.update {
                    it.copy(
                        isHandheldConnectionLost = !isConnected,
                    )
                }
            }
        }
    }

    private fun makeInitialUiState(): UiState =
        UiState(
            isHandheldConnectionLost = false,
        )

    data class UiState(
        val isHandheldConnectionLost: Boolean,
    )
}