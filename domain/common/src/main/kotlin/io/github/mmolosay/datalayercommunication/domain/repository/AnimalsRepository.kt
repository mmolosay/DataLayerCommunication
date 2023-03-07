package io.github.mmolosay.datalayercommunication.domain.repository

import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.models.Animals
import io.github.mmolosay.datalayercommunication.utils.resource.Resource

interface AnimalsRepository {
    suspend fun getAllAnimals(): Resource<Animals>
    suspend fun deleteAnimalById(id: Long): Resource<Animal?>
}