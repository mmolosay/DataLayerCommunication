package io.github.mmolosay.datalayercommunication.data.handheld

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success

class AnimalsRepositoryImpl(
    animals: List<Animal>
) : AnimalsRepository {

    private val animals = animals.toMutableList()

    override suspend fun getAllAnimals(): Resource<List<Animal>> =
        Resource.success(animals)

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> {
        if (animals.isEmpty()) return Resource.success(null)
        return animals
            .firstOrNull { it.id == id }
            ?.also { animal ->
                animals.remove(animal)
            }
            .let { Resource.success(it) }
    }
}