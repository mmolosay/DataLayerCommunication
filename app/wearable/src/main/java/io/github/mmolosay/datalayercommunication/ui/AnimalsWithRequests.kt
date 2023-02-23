package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListScope
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.viewmodel.WearableViewModel.UiState

// region Previews

@Preview
@Composable
private fun ConnectedStatePreview() =
    AnimalsWithRequests(
        uiState = previewUiState(),
        scalingLazyListState = rememberScalingLazyListState(),
        onGetAllAnimalsClick = {},
        onDeleteRandomCatClick = {},
        onClearClick = {},
    )

private fun previewUiState(): UiState =
    UiState(
        showConnectionFailure = true,
        elapsedTime = "3569 ms",
        animals = emptyList(),
    )

// endregion

@Composable
fun AnimalsWithRequests(
    uiState: UiState,
    scalingLazyListState: ScalingLazyListState,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    ScalingLazyColumn(
        state = scalingLazyListState,
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 32.dp
        )
    ) {
        item {
            MessageButton(
                onClick = onGetAllAnimalsClick,
                text = "Get all animals",
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            MessageButton(
                onClick = onDeleteRandomCatClick,
                text = "Delete ranom cat",
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Text(text = "Request elapsed time: ${uiState.elapsedTime}")
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimalsSection(uiState.animals)
        item {
            Button(
                onClick = onClearClick,
                colors = ButtonDefaults.secondaryButtonColors(),
            ) {
                Text(
                    text = "Clear output",
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun MessageButton(
    onClick: () -> Unit,
    text: String,
) =
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }

private fun ScalingLazyListScope.AnimalsSection(animals: List<Animal>) =
    items(animals) {
        Animal(it)
    }

@Composable
private fun Animal(animal: Animal) =
    Card(onClick = {}) {
        Text(text = "ID: ${animal.id}")
        Text(text = "Species: ${animal.species}")
        Text(text = "Name: ${animal.name}")
        Text(text = "Age: ${animal.age}")
    }