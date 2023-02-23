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
import io.github.mmolosay.datalayercommunication.utils.resource.map
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
    private val isConnectedToHandheldDeviceUseCase: CheckIsConnectedToHandheldDeviceUseCase,
) : ViewModel() {

    val uiState = mutableStateOf(makeBlankUiState())

    init {
        launchRepeatingConnectionCheck(2_000L)
    }

    fun getAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimalsUseCase()
            }
            uiState.value = uiState.value.copy(
                showConnectionFailure = resource is ConnectionFailure,
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
                showConnectionFailure = resource is ConnectionFailure,
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
                showConnectionFailure = resource is ConnectionFailure,
                elapsedTime = makeElapsedTime(elapsed),
                animals = resource.map { animal -> animal?.let { listOf(it) } ?: emptyList() }
            )
        }
    }

    fun clearAnimals() {
        uiState.value = makeBlankUiState()
    }

    fun launchConnectionCheck(): Job =
        viewModelScope.launch {
            uiState.value = uiState.value.copy(
                showConnectionFailure = !isConnectedToHandheldDeviceUseCase(),
            )
        }

    private fun launchRepeatingConnectionCheck(intervalMillis: Long) =
        viewModelScope.launch {
            while (isActive) {
                val elapsed = measureTimeMillis { launchConnectionCheck().join() }
                val left = intervalMillis - elapsed
                delay(left)
            }
        }

    private fun makeBlankUiState(): UiState =
        UiState(
            showConnectionFailure = false,
            elapsedTime = "—",
            animals = Resource.success(emptyList()),
        )

    private fun makeElapsedTime(ms: Long): String =
        "$ms ms"

    data class UiState(
        val showConnectionFailure: Boolean,
        val elapsedTime: String,
        val animals: Resource<List<Animal>>,
    )
}
