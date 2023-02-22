package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.CheckIsConnectedToHandheldDeviceUseCase
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.map
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
    private val isConnectedToHandheldDeviceUseCase: CheckIsConnectedToHandheldDeviceUseCase,
) : ViewModel() {

    val uiState = mutableStateOf(makeInitialUiState())

    init {
        launchRepeatingConnectionCheck()
    }

    fun getAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimalsUseCase()
            }
            uiState.value = uiState.value.copy(
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
                elapsedTime = makeElapsedTime(elapsed),
                animals = resource.map { animal -> animal?.let { listOf(it) } ?: emptyList() }
            )
        }
    }

    fun clearAnimals() {
        uiState.value = makeBlankUiState()
    }

    private fun launchRepeatingConnectionCheck() =
        viewModelScope.launch {
            while (isActive) {
                uiState.value = uiState.value.copy(
                    isConnected = isConnectedToHandheldDeviceUseCase(),
                )
                delay(2.seconds)
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

    data class UiState(
        val isConnected: Boolean,
        val elapsedTime: String,
        val animals: Resource<List<Animal>>,
    )
}
