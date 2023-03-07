package io.github.mmolosay.datalayercommunication.data.handheld

import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.models.Animals
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimalsRepositoryImpl(
    animals: List<Animal>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : AnimalsRepository {

    private val animals = animals.toMutableList()

    override suspend fun getAllAnimals(): Resource<Animals> =
        withContext(dispatcher) {
            Animals(animals)
                .let { Resource.success(it) }
        }

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> =
        withContext(dispatcher) block@{
            if (animals.isEmpty()) return@block Resource.success(null)
            animals
                .firstOrNull { it.id == id }
                ?.also { animal ->
                    animals.remove(animal)
                }
                .let { Resource.success(it) }
        }
}