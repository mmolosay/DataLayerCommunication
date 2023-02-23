package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.CheckIsConnectedToHandheldDeviceUseCase
import io.github.mmolosay.datalayercommunication.models.UiState
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrNull
import io.github.mmolosay.datalayercommunication.utils.resource.isSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimals: GetAnimalsUseCase,
    private val deleteRandomAnimal: DeleteRandomAnimalUseCase,
    private val isConnectedToHandheldDevice: CheckIsConnectedToHandheldDeviceUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(makeInitialUiState())
        private set

    private var connectionCheckJob: Job? = null

    init {
        launchRepeatingConnectionCheck()
    }

    fun executeGetAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            uiState = uiState.copy(
                showConnectionFailure = resource is ConnectionFailure,
                elapsedTime = makeElapsedTimeOrBlank(elapsed),
                animals = resource.getOrNull() ?: emptyList(),
            )
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
            uiState = uiState.copy(
                showConnectionFailure = resource is ConnectionFailure,
                elapsedTime = makeElapsedTimeOrBlank(elapsed),
                animals = resource.getOrNull()?.let { listOf(it) } ?: emptyList(),
            )
        }
    }

    fun clearOutput() {
        uiState = uiState.copy(
            elapsedTime = makeBlankElapsedTime(),
            animals = emptyList(),
        )
    }

    fun launchConnectionCheck(): Job {
        connectionCheckJob?.cancel()
        return viewModelScope.launch {
            uiState = uiState.copy(
                showConnectionFailure = !isConnectedToHandheldDevice(),
            )
        }.also { job ->
            connectionCheckJob = job
        }
    }

    private fun launchRepeatingConnectionCheck() =
        viewModelScope.launch {
            while (isActive) {
                val elapsed = measureTimeMillis { launchConnectionCheck().join() }
                val left = CONNECTION_CHECK_INTERVAL_MILLIS - elapsed
                delay(left)
            }
        }

    private fun makeInitialUiState(): UiState =
        UiState(
            showConnectionFailure = false,
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

    companion object {
        private const val CONNECTION_CHECK_INTERVAL_MILLIS = 2_000L
    }
}
