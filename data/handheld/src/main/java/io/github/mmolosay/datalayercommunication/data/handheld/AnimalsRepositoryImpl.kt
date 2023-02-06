package io.github.mmolosay.datalayercommunication.data.handheld

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository

class AnimalsRepositoryImpl(
    animals: List<Animal>
) : AnimalsRepository {

    private val animals = animals.toMutableList()

    override suspend fun getAllAnimals(): List<Animal> {
        return animals
    }

    override suspend fun deleteAnimalById(id: Long): Animal? {
        if (animals.isEmpty()) return null
        return animals
            .firstOrNull { it.id == id }
            ?.also { animal ->
                animals.remove(animal)
            }
    }
}