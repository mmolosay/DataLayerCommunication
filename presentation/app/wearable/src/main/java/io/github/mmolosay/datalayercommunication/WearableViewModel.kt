/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mmolosay.datalayercommunication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.usecase.DeleteRandomAnimalUseCase
import io.github.mmolosay.datalayercommunication.domain.usecase.GetAnimalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class WearableViewModel @Inject constructor(
    private val getAnimalsUseCase: GetAnimalsUseCase,
    private val deleteRandomAnimalUseCase: DeleteRandomAnimalUseCase,
) :
    ViewModel() {

    val uiState = mutableStateOf(makeBlankUiState())

    fun getAllAnimals() {
        viewModelScope.launch {
            val animals: List<Animal>
            val elapsed = measureTimeMillis {
                animals = getAnimalsUseCase(
                    amount = 5,
                    ageFrom = 1,
                    ageTo = null,
                    onlyCats = false,
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
