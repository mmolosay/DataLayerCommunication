package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.failures.CommunicationFailures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.CheckIsConnectedToHandheldDeviceUseCase
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.fold
import io.github.mmolosay.datalayercommunication.utils.resource.map
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
    private val isConnectedToHandheldDeviceUseCase: CheckIsConnectedToHandheldDeviceUseCase,
) : ViewModel() {

    val uiState = mutableStateOf(makeInitialUiState())

    private var connectionCheckJob: Job? = null

    init {
        launchRepeatingConnectionCheck(2_000L)
    }

    override fun onCleared() {
        super.onCleared()
        connectionCheckJob?.cancel()
    }

    fun getAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimalsUseCase()
            }
            uiState.value = uiState.value.copy(
                isConnected = !resource.isConnectionFailure(),
                elapsedTime = makeElapsedTime(elapsed),
                animals = resource,
            )
        }
    }

    fun getCatsOlderThan1() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimalsUseCase(
                    ageFrom = 2,
                    onlyCats = true,
                )
            }
            uiState.value = uiState.value.copy(
                isConnected = !resource.isConnectionFailure(),
                elapsedTime = makeElapsedTime(elapsed),
                animals = resource,
            )
        }
    }

    fun deleteRandomAnimal(
        ofSpecies: Animal.Species? = null,
        olderThan: Int? = null,
    ) {
        viewModelScope.launch {
            val resource: Resource<Animal?>
            val elapsed = measureTimeMillis {
                resource = deleteRandomAnimalUseCase(ofSpecies, olderThan)
            }
            uiState.value = uiState.value.copy(
                isConnected = !resource.isConnectionFailure(),
                elapsedTime = makeElapsedTime(elapsed),
                animals = resource.map { animal -> animal?.let { listOf(it) } ?: emptyList() }
            )
        }
    }

    fun clearAnimals() {
        uiState.value = makeBlankUiState()
    }

    fun launchConnectionCheck() {
        connectionCheckJob?.cancel()
        connectionCheckJob = viewModelScope.launch {
            uiState.value = uiState.value.copy(
                isConnected = isConnectedToHandheldDeviceUseCase(),
            )
        }
    }

    private fun launchRepeatingConnectionCheck(intervalMillis: Long) =
        viewModelScope.launch {
            var completionTime = Date().time
            while (isActive) {
                launchConnectionCheck()
                connectionCheckJob?.join()
                completionTime = Date().time.also { now ->
                    val elapsed = now - completionTime
                    val left = intervalMillis - elapsed
                    delay(left)
                }
            }
        }

    private fun makeInitialUiState(): UiState =
        UiState(
            isConnected = false,
            elapsedTime = "",
            animals = Resource.success(emptyList()),
        )

    private fun makeBlankUiState(): UiState =
        UiState(
            isConnected = true,
            elapsedTime = "—",
            animals = Resource.success(emptyList()),
        )

    private fun makeElapsedTime(ms: Long): String =
        "$ms ms"

    private fun Resource<*>.isConnectionFailure(): Boolean =
        fold(
            onSuccess = { false },
            onFailure = { it is ConnectionFailure },
        )

    data class UiState(
        val isConnected: Boolean,
        val elapsedTime: String,
        val animals: Resource<List<Animal>>,
    )
}