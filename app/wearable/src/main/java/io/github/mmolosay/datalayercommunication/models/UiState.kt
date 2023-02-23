package io.github.mmolosay.datalayercommunication.models

import io.github.mmolosay.datalayercommunication.domain.model.Animal

data class UiState(
    val showConnectionFailure: Boolean,
    val elapsedTime: String,
    val animals: List<Animal>,
)