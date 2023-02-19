package io.github.mmolosay.datalayercommunication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.map
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
) : ViewModel() {

    val uiState = mutableStateOf(makeBlankUiState())

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

    private fun makeBlankUiState(): UiState =
        UiState(
            elapsedTime = "—",
            animals = Resource.success(emptyList()),
        )

    private fun makeElapsedTime(ms: Long): String =
        "$ms ms"

    data class UiState(
        val elapsedTime: String,
        val animals: Resource<List<Animal>>,
    )
}
