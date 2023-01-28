package com.example.domain

interface AnimalsRepository {
    suspend fun getAllAnimals(): List<Animal>
    suspend fun deleteAnimalById(id: Long): Animal?
}