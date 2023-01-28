package io.github.mmolosay.datalayercommunication.domain.repository

import io.github.mmolosay.datalayercommunication.domain.model.Animal

interface AnimalsRepository {
    suspend fun getAllAnimals(): List<Animal>
    suspend fun deleteAnimalById(id: Long): Animal?
}