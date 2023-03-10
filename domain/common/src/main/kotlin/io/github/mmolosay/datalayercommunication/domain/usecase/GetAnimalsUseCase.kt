package io.github.mmolosay.datalayercommunication.domain.usecase

import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.data.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrElse
import io.github.mmolosay.datalayercommunication.utils.resource.success
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
            .list
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