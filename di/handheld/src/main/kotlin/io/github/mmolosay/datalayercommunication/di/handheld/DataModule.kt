package io.github.mmolosay.datalayercommunication.di.handheld

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.data.handheld.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.data.AnimalsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAnimalsRepository(): AnimalsRepository =
        AnimalsRepositoryImpl(animals)

    private val animals = listOf(
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
            age = 2,
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
}