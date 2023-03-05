package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
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
    private val getAnimals: GetAnimalsUseCase,
    private val deleteRandomAnimal: DeleteRandomAnimalUseCase,
    private val handheldConnectionStateProvider: ConnectionStateProvider,
) : ViewModel() {

    private val fullUiState = MutableStateFlow(makeInitialUiState())
    val uiState: StateFlow<UiState> = fullUiState
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), fullUiState.value.toUiState())

    init {
        observeHandheldConnectionState()
    }

    fun executeGetAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            fullUiState.update {
                it.copy(
                    showHandheldNotConnected = resource is ConnectionFailure,
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
                    showHandheldNotConnected = resource is ConnectionFailure,
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

    private fun observeHandheldConnectionState() {
        viewModelScope.launch {
            handheldConnectionStateProvider.connectionStateFlow.collect { isConnected ->
                fullUiState.update {
                    // we want to show loading for minimum some noticable time to prevent it blinking
                    if (it.showLoading) {
                        val now = System.currentTimeMillis()
                        val elapsed = (now - it.loadingStarted).milliseconds
                        val minimumScreenTime = 1_000.milliseconds
                        val screenTimeLeft = minimumScreenTime - elapsed
                        delay(screenTimeLeft) // skipped if <= 0
                    }
                    it.copy(
                        showLoading = false, // dismiss loading, when first connection state arrives
                        showHandheldNotConnected = !isConnected,
                    )
                }
            }
        }
    }

    private fun makeInitialUiState(): FullUiState =
        FullUiState(
            loadingStarted = System.currentTimeMillis(),
            showLoading = true,
            showHandheldNotConnected = false,
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

    /**
     * Used to remember values, when type of [UiState] changes.
     *
     * For example, when we go from [UiState.Content] to [UiState.HandheldNotConnected],
     * and then back to [UiState.Content], we can't use its previous values, because was
     * propagated to flow, and thus not accessible anymore.
     */
    private data class FullUiState(
        val loadingStarted: Long,
        val showLoading: Boolean,
        val showHandheldNotConnected: Boolean,
        val elapsedTime: String,
        val animals: List<Animal>,
    )

    private fun FullUiState.toUiState(): UiState =
        when {
            showLoading -> UiState.Loading
            showHandheldNotConnected -> UiState.HandheldNotConnected
            else -> UiState.Content(
                elapsedTime = elapsedTime,
                animals = animals,
            )
        }
}
