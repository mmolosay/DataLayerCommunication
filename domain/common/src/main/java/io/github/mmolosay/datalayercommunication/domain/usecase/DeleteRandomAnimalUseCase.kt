package io.github.mmolosay.datalayercommunication.domain.usecase

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import javax.inject.Inject

class DeleteRandomAnimalUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    suspend operator fun invoke(
        ofSpecies: Animal.Species?,
        olderThan: Int?,
    ): Animal? =
        repository
            .getAllAnimals()
            .asSequence()
            .run {
                ofSpecies?.let { filter { it.species == ofSpecies } } ?: this
            }
            .run {
                olderThan?.let { filter { it.age > olderThan } } ?: this
            }
            .toList()
            .randomOrNull()
            ?.also { animal ->
                repository.deleteAnimalById(animal.id)
            }
}