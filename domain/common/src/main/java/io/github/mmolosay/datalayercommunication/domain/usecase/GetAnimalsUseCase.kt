package io.github.mmolosay.datalayercommunication.domain.usecase

import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    suspend operator fun invoke(
        ageFrom: Int? = null,
        ageTo: Int? = null,
        onlyCats: Boolean = false,
    ): List<Animal> =
        repository
            .getAllAnimals()
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
}