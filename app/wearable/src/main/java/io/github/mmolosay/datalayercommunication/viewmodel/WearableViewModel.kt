package io.github.mmolosay.datalayercommunication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.CheckIsConnectedToHandheldDeviceUseCase
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
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
    private val isConnectedToHandheldDeviceUseCase: CheckIsConnectedToHandheldDeviceUseCase,
) : ViewModel() {

    val uiState = mutableStateOf(makeBlankUiState())

    private var connectionCheckJob: Job? = null

    init {
        launchRepeatingConnectionCheck(2_000L)
    }

    fun getAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimalsUseCase()
            }.takeIf { resource.isSuccess }
            uiState.value = uiState.value.copy(
                showConnectionFailure = resource is ConnectionFailure,
                elapsedTime = makeElapsedTimeOrBlank(elapsed),
                animals = resource.getOrNull() ?: emptyList(),
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
            }.takeIf { resource.isSuccess }
            uiState.value = uiState.value.copy(
                showConnectionFailure = resource is ConnectionFailure,
                elapsedTime = makeElapsedTimeOrBlank(elapsed),
                animals = resource.getOrNull()?.let { listOf(it) } ?: emptyList(),
            )
        }
    }

    fun clearAnimals() {
        uiState.value = makeBlankUiState()
    }

    fun launchConnectionCheck(): Job {
        connectionCheckJob?.cancel()
        return viewModelScope.launch {
            uiState.value = uiState.value.copy(
                showConnectionFailure = !isConnectedToHandheldDeviceUseCase(),
            )
        }.also { job ->
            connectionCheckJob = job
        }
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

    data class UiState(
        val showConnectionFailure: Boolean,
        val elapsedTime: String,
        val animals: List<Animal>,
    )
}
