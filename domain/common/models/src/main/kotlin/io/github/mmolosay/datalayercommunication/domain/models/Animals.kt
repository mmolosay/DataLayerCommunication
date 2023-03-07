package io.github.mmolosay.datalayercommunication.domain.models

import kotlinx.serialization.Serializable

// we need individual wrapper class for a list of Animals to be able to use it with Resource.
// otherwise, having Resource<List<Animal>> will crash with:
// "Class 'ArrayList' is not registered for polymorphic serialization in the scope of 'Any'."
// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#polymorphism-and-generic-classes
@Serializable
data class Animals(
    val list: List<Animal>,
)