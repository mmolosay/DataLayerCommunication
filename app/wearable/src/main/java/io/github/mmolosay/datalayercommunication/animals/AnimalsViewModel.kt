package io.github.mmolosay.datalayercommunication.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.models.Animal
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
class AnimalsViewModel @Inject constructor(
    private val getAnimals: GetAnimalsUseCase,
    private val deleteRandomAnimal: DeleteRandomAnimalUseCase,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(makeInitialUiState())
    val uiState = mutableUiState.asStateFlow()

    fun executeGetAllAnimals(
        onConnectionFailure: () -> Unit,
    ) {
        viewModelScope.launch {
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            // TODO: refactor?
            if (resource is ConnectionFailure) onConnectionFailure()
            mutableUiState.update {
                it.copy(
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull() ?: emptyList(),
                )
            }
        }
    }

    fun executeDeleteRandomAnimal(
        ofSpecies: Animal.Species? = null,
        olderThan: Int? = null,
        onConnectionFailure: () -> Unit,
    ) {
        viewModelScope.launch {
            val resource: Resource<Animal?>
            val elapsed = measureTimeMillis {
                resource = deleteRandomAnimal(ofSpecies, olderThan)
            }.takeIf { resource.isSuccess }
            // TODO: refactor?
            if (resource is ConnectionFailure) onConnectionFailure()
            mutableUiState.update {
                it.copy(
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull()?.let { listOf(it) } ?: emptyList(),
                )
            }
        }
    }

    fun clearOutput() {
        mutableUiState.update {
            it.copy(
                elapsedTime = makeBlankElapsedTime(),
                animals = emptyList(),
            )
        }
    }

    // region View models

    private fun makeInitialUiState(): UiState =
        UiState(
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
}
