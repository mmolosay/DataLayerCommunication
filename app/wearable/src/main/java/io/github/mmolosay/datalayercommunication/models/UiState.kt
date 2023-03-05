package io.github.mmolosay.datalayercommunication.models

import androidx.compose.runtime.Stable
import io.github.mmolosay.datalayercommunication.domain.model.Animal

@Stable
data class UiState(
    val showLoading: Boolean,
    val showConnectionFailure: Boolean,
    val elapsedTime: String,
    val animals: List<Animal>,
)