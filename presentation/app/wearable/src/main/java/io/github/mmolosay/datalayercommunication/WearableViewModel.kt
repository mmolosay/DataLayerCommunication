package io.github.mmolosay.datalayercommunication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
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
            val animals: List<Animal>
            val elapsed = measureTimeMillis {
                animals = getAnimalsUseCase()
            }
            uiState.value = uiState.value.copy(
                elapsedTime = makeElapsedTime(elapsed),
                animals = animals,
            )
        }
    }

    fun getCatsOlderThan1() {
        viewModelScope.launch {
            val animals: List<Animal>
            val elapsed = measureTimeMillis {
                animals = getAnimalsUseCase(
                    ageFrom = 2,
                    onlyCats = true,
                )
            }
            uiState.value = uiState.value.copy(
                elapsedTime = makeElapsedTime(elapsed),
                animals = animals,
            )
        }
    }

    fun deleteRandomAnimal(
        ofSpecies: Animal.Species? = null,
        olderThan: Int? = null,
    ) {
        viewModelScope.launch {
            val animal: Animal?
            val elapsed = measureTimeMillis {
                animal = deleteRandomAnimalUseCase(ofSpecies, olderThan)
            }
            uiState.value = uiState.value.copy(
                elapsedTime = makeElapsedTime(elapsed),
                animals = animal?.let { listOf(it) } ?: emptyList(),
            )
        }
    }

    fun clearAnimals() {
        uiState.value = makeBlankUiState()
    }

    private fun makeBlankUiState(): UiState =
        UiState(
            elapsedTime = "—",
            animals = emptyList(),
        )

    private fun makeElapsedTime(ms: Long): String =
        "$ms ms"

    data class UiState(
        val elapsedTime: String,
        val animals: List<Animal>
    )
}
