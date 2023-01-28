package io.github.mmolosay.datalayercommunication.data.handheld

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import kotlinx.coroutines.delay

class AnimalsRepositoryImpl : AnimalsRepository {

    // represents some data source
    private val animals = mutableListOf(
        Animal(
            id = 0L,
            species = Animal.Species.Dog,
            name = "Jake",
            age = 3,
        ),
        Animal(
            id = 1L,
            species = Animal.Species.Cat,
            name = "Cassy",
            age = 1,
        ),
        Animal(
            id = 2L,
            species = Animal.Species.Rat,
            name = "Scabbers",
            age = 12,
        ),
        Animal(
            id = 3L,
            species = Animal.Species.Owl,
            name = "Pig",
            age = 1,
        ),
        Animal(
            id = 4L,
            species = Animal.Species.Cat,
            name = "Sting",
            age = 4,
        ),
        Animal(
            id = 5L,
            species = Animal.Species.Dog,
            name = "Rocket",
            age = 5,
        ),
        Animal(
            id = 6L,
            species = Animal.Species.Cat,
            name = "Purr",
            age = 0,
        ),
        Animal(
            id = 7L,
            species = Animal.Species.Owl,
            name = "Merlin",
            age = 2,
        ),
    )

    override suspend fun getAllAnimals(): List<Animal> {
        delay(2000L) // imation of time-consuming operation
        return animals
    }

    override suspend fun deleteAnimalById(id: Long): Animal? {
        delay(2000L) // imation of time-consuming operation
        if (animals.isEmpty()) return null
        return animals
            .firstOrNull { it.id == id }
            ?.also { animal ->
                animals.remove(animal)
            }
    }
}