package io.github.mmolosay.datalayercommunication.domain.usecase

import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.data.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrElse
import io.github.mmolosay.datalayercommunication.utils.resource.success
import javax.inject.Inject

class DeleteRandomAnimalUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    suspend operator fun invoke(
        ofSpecies: Animal.Species?,
        olderThan: Int?,
    ): Resource<Animal?> {
        return repository
            .getAllAnimals()
            .getOrElse { return it }
            .list
            .asSequence()
            .run {
                ofSpecies?.let { filter { it.species == ofSpecies } } ?: this
            }
            .run {
                olderThan?.let { filter { it.age > olderThan } } ?: this
            }
            .toList()
            .randomOrNull()
            ?.let { animalToDelete ->
                repository.deleteAnimalById(animalToDelete.id)
            }
            ?: Resource.success(null)
    }
}