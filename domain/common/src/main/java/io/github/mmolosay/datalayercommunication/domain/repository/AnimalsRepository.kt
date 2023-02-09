package io.github.mmolosay.datalayercommunication.domain.repository

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.resource.Resource

interface AnimalsRepository {
    suspend fun getAllAnimals(): Resource<List<Animal>>
    suspend fun deleteAnimalById(id: Long): Resource<Animal?>
}