package io.github.mmolosay.datalayercommunication.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.github.mmolosay.datalayercommunication.domain.model.Animal

@Stable
sealed interface UiState {

    @Immutable
    object Loading : UiState

    @Immutable
    object HandheldNotConnected : UiState

    @Stable
    data class Content(
        val elapsedTime: String,
        val animals: List<Animal>,
    ) : UiState
}