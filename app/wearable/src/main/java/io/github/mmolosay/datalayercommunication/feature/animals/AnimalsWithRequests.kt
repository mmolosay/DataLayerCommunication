package io.github.mmolosay.datalayercommunication.feature.animals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListScope
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import com.ramcosta.composedestinations.annotation.Destination
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.feature.animals.AnimalsViewModel.UiState
import io.github.mmolosay.datalayercommunication.feature.connection.ConnectionViewModel
import io.github.mmolosay.datalayercommunication.feature.connection.HandheldConnectionLostOverlay
import io.github.mmolosay.datalayercommunication.feature.connection.observeHandheldConnectionState
import io.github.mmolosay.datalayercommunication.ui.navigation.MainAppNavGraph

// region Previews

@Preview
@Composable
private fun AnimalsWithRequestsPreview() =
    AnimalsWithRequests(
        uiState = previewUiState(),
        scalingLazyListState = rememberScalingLazyListState(),
        onGetAllAnimalsClick = {},
        onDeleteRandomCatClick = {},
        onClearOutputClick = {},
    )

private fun previewUiState(): UiState =
    UiState(
        elapsedTime = "3569 ms",
        animals = emptyList(),
    )

// endregion

@MainAppNavGraph(start = true)
@Destination
@Composable
fun AnimalsWithRequests(
    animalsVM: AnimalsViewModel = hiltViewModel(),
    connectionVM: ConnectionViewModel,
    scalingLazyListState: ScalingLazyListState,
) {
    val uiState by animalsVM.uiState.collectAsStateWithLifecycle()
    var shouldShowHandheldConnectionLost by remember { mutableStateOf(false) }

    val showHandheldConnectionLost = remember {
        { shouldShowHandheldConnectionLost = true }
    }
    val onGetAllAnimalsClick = remember {
        {
            animalsVM.executeGetAllAnimals(
                onConnectionFailure = showHandheldConnectionLost,
            )
        }
    }
    val onDeleteRandomCatClick = remember {
        {
            animalsVM.executeDeleteRandomAnimal(
                ofSpecies = Animal.Species.Cat,
                onConnectionFailure = showHandheldConnectionLost,
            )
        }
    }
    val onClearOutputClick = remember {
        { animalsVM.clearOutput() }
    }

    observeHandheldConnectionState(
        connectionVM = connectionVM,
        onChanged = { isConnectionLost -> shouldShowHandheldConnectionLost = isConnectionLost },
    )
    if (shouldShowHandheldConnectionLost)
        HandheldConnectionLostOverlay()
    else
        AnimalsWithRequests(
            uiState = uiState,
            scalingLazyListState = scalingLazyListState,
            onGetAllAnimalsClick = onGetAllAnimalsClick,
            onDeleteRandomCatClick = onDeleteRandomCatClick,
            onClearOutputClick = onClearOutputClick,
        )
}

@Composable
fun AnimalsWithRequests(
    uiState: UiState,
    scalingLazyListState: ScalingLazyListState,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearOutputClick: () -> Unit,
) {
    ScalingLazyColumn(
        state = scalingLazyListState,
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 24.dp,
        ),
    ) {
        RequestButtonsItems(
            onGetAllAnimalsClick = onGetAllAnimalsClick,
            onDeleteRandomCatClick = onDeleteRandomCatClick,
        )
        ElapsedTimeItem(
            elapsedTime = uiState.elapsedTime,
        )
        AnimalsItem(
            animals = uiState.animals,
        )
        ClearOutputItem(
            onClick = onClearOutputClick,
        )
    }
}

// region Items

private fun ScalingLazyListScope.RequestButtonsItems(
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
) {
    item {
        GetAllAnimalsButton(
            onClick = onGetAllAnimalsClick,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    item {
        DeleteRandomCatButton(
            onClick = onDeleteRandomCatClick,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun ScalingLazyListScope.ElapsedTimeItem(
    elapsedTime: String,
) =
    item {
        Text(
            text = "Request elapsed time: $elapsedTime",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.caption3,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

private fun ScalingLazyListScope.AnimalsItem(animals: List<Animal>) =
    items(animals) {
        Animal(it)
    }

private fun ScalingLazyListScope.ClearOutputItem(
    onClick: () -> Unit,
) =
    item {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.secondaryButtonColors(),
        ) {
            Text(
                text = "Clear output",
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
    }

// endregion

// region Request buttons

@Composable
private fun GetAllAnimalsButton(
    // TODO: try passing modifier with bottom padding instead of Spacer
    onClick: () -> Unit,
) =
    RequestButton(
        onClick = onClick,
        text = "Get all animals",
    )

@Composable
private fun DeleteRandomCatButton(
    onClick: () -> Unit,
) =
    RequestButton(
        onClick = onClick,
        text = "Delete random cat",
    )

@Composable
private fun RequestButton(
    onClick: () -> Unit,
    text: String,
) =
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }

// endregion

// region Animals

@Composable
private fun Animal(animal: Animal) =
    Card(onClick = {}) {
        Text(text = "ID: ${animal.id}")
        Text(text = "Species: ${animal.species}")
        Text(text = "Name: ${animal.name}")
        Text(text = "Age: ${animal.age}")
    }

// endregion