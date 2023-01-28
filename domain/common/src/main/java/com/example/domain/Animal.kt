package com.example.domain

import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    val id: Long,
    val species: Species,
    val name: String,
    val age: Int,
) {

    @Serializable
    enum class Species {
        Cat, Dog, Owl, Rat,
    }
}