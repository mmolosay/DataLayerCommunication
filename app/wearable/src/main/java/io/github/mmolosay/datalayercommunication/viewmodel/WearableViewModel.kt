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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimals: GetAnimalsUseCase,
    private val deleteRandomAnimal: DeleteRandomAnimalUseCase,
    private val handheldConnectionStateProvider: ConnectionStateProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(makeInitialUiState())
    val uiState = _uiState.asStateFlow()

    init {
        launchRepeatingHandheldConnectionCheck()
        observeHandheldConnectionState()
    }

    fun executeGetAllAnimals() {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            _uiState.update {
                it.copy(
                    showConnectionFailure = resource is ConnectionFailure,
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
            _uiState.update {
                it.copy(
                    showConnectionFailure = resource is ConnectionFailure,
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull()?.let { listOf(it) } ?: emptyList(),
                )
            }
        }
    }

    fun clearOutput() {
        _uiState.update {
            it.copy(
                elapsedTime = makeBlankElapsedTime(),
                animals = emptyList(),
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        handheldConnectionStateProvider.stop()
    }

    private fun launchRepeatingHandheldConnectionCheck() {
        handheldConnectionStateProvider.start(viewModelScope)
    }

    private fun observeHandheldConnectionState() {
        viewModelScope.launch {
            handheldConnectionStateProvider.connectionStateFlow.collect { isConnected ->
                _uiState.update {
                    it.copy(
                        showLoading = false, // dismiss loading, when first connection state arrives
                        showConnectionFailure = !isConnected,
                    )
                }
            }
        }
    }

    private fun makeInitialUiState(): UiState =
        UiState(
            showLoading = true,
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
}
