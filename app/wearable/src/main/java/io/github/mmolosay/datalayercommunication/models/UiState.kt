package io.github.mmolosay.datalayercommunication.models

import io.github.mmolosay.datalayercommunication.domain.models.Animal

// TODO: move inside AnimalsViewModel
sealed interface UiState {

    object HandheldConnectionLost : UiState // TODO: make an individual (thus reusable) destination

    data class Content(
        val elapsedTime: String,
        val animals: List<Animal>,
    ) : UiState
}