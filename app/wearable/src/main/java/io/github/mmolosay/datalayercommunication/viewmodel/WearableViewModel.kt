package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionFlowProvider
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.usecase.CheckIsHandheldDeviceConnectedUseCase
import io.github.mmolosay.datalayercommunication.models.UiState
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrNull
import io.github.mmolosay.datalayercommunication.utils.resource.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val isHandheldDeviceConnected: CheckIsHandheldDeviceConnectedUseCase,
    private val handheldConnectionFlowProvider: Lazy<ConnectionFlowProvider?>,
    private val getAnimals: GetAnimalsUseCase,
    private val deleteRandomAnimal: DeleteRandomAnimalUseCase,
) : ViewModel() {

    private val fullUiState = MutableStateFlow(makeInitialUiState())
    val uiState: StateFlow<UiState> = fullUiState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), fullUiState.value.toUiState())

    init {
        setUp()
    }

    fun executeGetAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            fullUiState.update {
                it.copy(
                    showHandheldConnectionLost = resource is ConnectionFailure,
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull() ?: emptyList(),
                )
            }
        }
    }

    fun executeDeleteRandomAnimal(
        ofSpecies: Animal.Species? = null,
        olderThan: Int? = null,
    ) {
        viewModelScope.launch {
            val resource: Resource<Animal?>
            val elapsed = measureTimeMillis {
                resource = deleteRandomAnimal(ofSpecies, olderThan)
            }.takeIf { resource.isSuccess }
            fullUiState.update {
                it.copy(
                    showHandheldConnectionLost = resource is ConnectionFailure,
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull()?.let { listOf(it) } ?: emptyList(),
                )
            }
        }
    }

    fun clearOutput() {
        fullUiState.update {
            it.copy(
                elapsedTime = makeBlankElapsedTime(),
                animals = emptyList(),
            )
        }
    }

    // region Set up

    private fun setUp() {
        viewModelScope.launch {
            executeCheckIsHandheldDeviceConnected()
            observeHandheldConnectionState()
        }
    }

    private suspend fun executeCheckIsHandheldDeviceConnected() {
        fullUiState.update {
            it.copy(showHandheldNotConnected = !isHandheldDeviceConnected())
        }
        dismissLoading()
    }

    private fun observeHandheldConnectionState() =
        viewModelScope.launch {
            handheldConnectionFlowProvider.get()?.connectionFlow?.collect { isConnected ->
                fullUiState.update {
                    it.copy(
                        showLoading = false, // dismiss loading, when first connection state arrives
                        showHandheldConnectionLost = !isConnected,
                    )
                }
            }
        }

    private suspend fun dismissLoading() {
        fullUiState.update {
            // show loading for minimum some noticable time to prevent it from blinking
            if (it.showLoading) {
                val now = System.currentTimeMillis()
                val elapsed = (now - it.loadingStartedTime).milliseconds
                val minimumScreenTime = 1_000.milliseconds
                val screenTimeLeft = minimumScreenTime - elapsed
                delay(screenTimeLeft) // skipped if <= 0
            }
            it.copy(showLoading = false)
        }
    }

    // endregion

    // region View models

    private fun makeInitialUiState(): FullUiState =
        FullUiState(
            loadingStartedTime = System.currentTimeMillis(),
            showLoading = true,
            showHandheldNotConnected = false,
            showHandheldConnectionLost = false,
            elapsedTime = makeBlankElapsedTime(),
            animals = emptyList(),
        )

    private fun makeElapsedTimeOrBlank(
        elapsedMillis: Long?,
    ): String =
        if (elapsedMillis != null) "$elapsedMillis ms"
        else makeBlankElapsedTime()

    private fun makeBlankElapsedTime(): String =
        "—"

    // endregion

    /**
     * Used to remember values, when type of [UiState] changes.
     *
     * For example, we emit following states to [uiState] flow:
     * 1. [UiState.Content],
     * 2. [UiState.HandheldConnectionLost],
     * 3. [UiState.Content] again (connection was restored).
     *
     * We can't obtain states, previously emitted by flow.
     * Thus, we can't create state from step #3 with data of state from step #1.
     */
    private data class FullUiState(
        val loadingStartedTime: Long,
        val showLoading: Boolean,
        val showHandheldNotConnected: Boolean,
        val showHandheldConnectionLost: Boolean,
        val elapsedTime: String,
        val animals: List<Animal>,
    )

    private fun FullUiState.toUiState(): UiState =
        when {
            showLoading -> UiState.Loading
            showHandheldNotConnected -> UiState.HandheldNotConnected
            showHandheldConnectionLost -> UiState.HandheldConnectionLost
            else -> UiState.Content(
                elapsedTime = elapsedTime,
                animals = animals,
            )
        }
}
