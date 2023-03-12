package io.github.mmolosay.datalayercommunication.models

import io.github.mmolosay.datalayercommunication.domain.models.Animal

// TODO: move inside AnimalsViewModel
data class UiState(
    val elapsedTime: String,
    val animals: List<Animal>,
)