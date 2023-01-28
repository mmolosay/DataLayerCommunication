package com.example.domain

import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(
    private val repository: AnimalsRepository,
) {

    suspend operator fun invoke(
        amount: Int,
        ageFrom: Int?,
        ageTo: Int?,
        onlyCats: Boolean,
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
            .take(amount)
            .toList()
}