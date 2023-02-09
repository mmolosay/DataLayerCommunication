package io.github.mmolosay.datalayercommunication.domain.usecase

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.getOrElse
import io.github.mmolosay.datalayercommunication.domain.resource.success
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    suspend operator fun invoke(
        ageFrom: Int? = null,
        ageTo: Int? = null,
        onlyCats: Boolean = false,
    ): Resource<List<Animal>> {
        return repository
            .getAllAnimals()
            .getOrElse { return it }
            .asSequence()
            .run {
                if (onlyCats) filter { it.species == Animal.Species.Cat } else this
            }
            .run {
                ageFrom?.let { age -> filter { it.age >= age } } ?: this
            }
            .run {
                ageTo?.let { age -> filter { it.age <= age } } ?: this
            }
            .toList()
            .let { Resource.success(it) }
    }
}