package io.github.mmolosay.datalayercommunication.feature.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrNull
import io.github.mmolosay.datalayercommunication.utils.resource.isSuccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var executingJob: Job? = null

    fun executeGetAllAnimals(
        onConnectionFailure: () -> Unit,
    ) {
        executingJob?.cancel()
        viewModelScope.launch {
            setUiStateLoading()
            val resource: Resource<List<Animal>>
            val elapsed = measureTimeMillis {
                resource = getAnimals()
            }.takeIf { resource.isSuccess }
            // TODO: refactor?
            if (resource is ConnectionFailure) onConnectionFailure()
            mutableUiState.value =
                UiState.AnimalsState.FetchedAnimals(
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animals = resource.getOrNull() ?: emptyList(),
                )
        }.also { executingJob = it }
    }

    fun executeDeleteRandomAnimal(
        ofSpecies: Animal.Species? = null,
        olderThan: Int? = null,
        onConnectionFailure: () -> Unit,
    ) {
        executingJob?.cancel()
        viewModelScope.launch {
            setUiStateLoading()
            val resource: Resource<Animal?>
            val elapsed = measureTimeMillis {
                resource = deleteRandomAnimal(ofSpecies, olderThan)
            }.takeIf { resource.isSuccess }
            // TODO: refactor?
            if (resource is ConnectionFailure) onConnectionFailure()
            mutableUiState.value =
                UiState.AnimalsState.DeletedAnimal(
                    elapsedTime = makeElapsedTimeOrBlank(elapsed),
                    animal = resource.getOrNull(),
                )
        }.also { executingJob = it }
    }

    fun clearOutput() {
        mutableUiState.value = UiState.Blank
    }

    // region View models

    private fun makeInitialUiState(): UiState =
        UiState.Blank

    private fun setUiStateLoading() {
        mutableUiState.value = UiState.Loading
    }

    private fun makeElapsedTimeOrBlank(
        elapsedMillis: Long?,
    ): String =
        if (elapsedMillis != null) "$elapsedMillis ms"
        else makeBlankElapsedTime()

    private fun makeBlankElapsedTime(): String =
        "—"

    // endregion

    sealed interface UiState {
        object Blank : UiState
        object Loading : UiState

        sealed interface AnimalsState : UiState {
            val elapsedTime: String

            data class FetchedAnimals(
                override val elapsedTime: String,
                val animals: List<Animal>,
            ) : AnimalsState

            data class DeletedAnimal(
                override val elapsedTime: String,
                val animal: Animal?
            ) : AnimalsState
        }

        // TODO: add failure?
    }

    override fun onCleared() {
        executingJob?.cancel()
    }
}
