package io.github.mmolosay.datalayercommunication.feature.animals

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
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
    UiState.AnimalsState.FetchedAnimals(
        elapsedTime = "3569 ms",
        animals = listOf(
            Animal(id = 1, species = Animal.Species.Cat, name = "Cat #1", age = 1),
        ),
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

    when {
        shouldShowHandheldConnectionLost ->
            HandheldConnectionLostOverlay()
        else ->
            AnimalsWithRequests(
                uiState = uiState,
                scalingLazyListState = scalingLazyListState,
                onGetAllAnimalsClick = onGetAllAnimalsClick,
                onDeleteRandomCatClick = onDeleteRandomCatClick,
                onClearOutputClick = onClearOutputClick,
            )
    }
}

@Composable
fun AnimalsWithRequests(
    uiState: UiState,
    scalingLazyListState: ScalingLazyListState,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearOutputClick: () -> Unit,
) =
    ScalingLazyColumn(
        state = scalingLazyListState,
        contentPadding = PaddingValues(
            horizontal = 8.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        anchorType = ScalingLazyListAnchorType.ItemCenter,
        autoCentering = AutoCenteringParams(itemIndex = 0, itemOffset = 0),
    ) {
        when (uiState) {
            is UiState.Blank ->
                itemsBlank(
                    onGetAllAnimalsClick = onGetAllAnimalsClick,
                    onDeleteRandomCatClick = onDeleteRandomCatClick,
                )
            is UiState.Loading ->
                itemsLoading(
                    onGetAllAnimalsClick = onGetAllAnimalsClick,
                    onDeleteRandomCatClick = onDeleteRandomCatClick,
                )
            is UiState.AnimalsState.FetchedAnimals ->
                itemsFetchedAnimals(
                    uiState = uiState,
                    onGetAllAnimalsClick = onGetAllAnimalsClick,
                    onDeleteRandomCatClick = onDeleteRandomCatClick,
                    onClearOutputClick = onClearOutputClick,
                )
            is UiState.AnimalsState.DeletedAnimal ->
                itemsDeletedAnimal(
                    uiState = uiState,
                    onGetAllAnimalsClick = onGetAllAnimalsClick,
                    onDeleteRandomCatClick = onDeleteRandomCatClick,
                    onClearOutputClick = onClearOutputClick,
                )
        }
    }

// region State items

private fun ScalingLazyListScope.itemsBlank(
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
) {
    RequestButtons(
        onGetAllAnimalsClick = onGetAllAnimalsClick,
        onDeleteRandomCatClick = onDeleteRandomCatClick,
    )
}

private fun ScalingLazyListScope.itemsLoading(
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
) {
    RequestButtons(
        onGetAllAnimalsClick = onGetAllAnimalsClick,
        onDeleteRandomCatClick = onDeleteRandomCatClick,
    )
    item {
        // note, that due to ScalingLazyColumn's contentPadding actual space will be greater
        Spacer(modifier = Modifier.height(4.dp))
    }
    Loading()
}

private fun ScalingLazyListScope.itemsFetchedAnimals(
    uiState: UiState.AnimalsState.FetchedAnimals,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearOutputClick: () -> Unit,
) {
    RequestButtons(
        onGetAllAnimalsClick = onGetAllAnimalsClick,
        onDeleteRandomCatClick = onDeleteRandomCatClick,
    )
    ElapsedTime(
        elapsedTime = uiState.elapsedTime,
    )
    FetchedAnimals(
        uiState = uiState,
    )
    ClearOutputButton(
        onClick = onClearOutputClick,
    )
}

private fun ScalingLazyListScope.itemsDeletedAnimal(
    uiState: UiState.AnimalsState.DeletedAnimal,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearOutputClick: () -> Unit,
) {
    RequestButtons(
        onGetAllAnimalsClick = onGetAllAnimalsClick,
        onDeleteRandomCatClick = onDeleteRandomCatClick,
    )
    ElapsedTime(
        elapsedTime = uiState.elapsedTime,
    )
    DeletedAnimal(
        uiState = uiState,
    )
    ClearOutputButton(
        onClick = onClearOutputClick,
    )
}

// endregion

// region Items

private fun ScalingLazyListScope.RequestButtons(
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
) {
    item {
        GetAllAnimalsButton(
            onClick = onGetAllAnimalsClick,
        )
    }
    item {
        DeleteRandomCatButton(
            onClick = onDeleteRandomCatClick,
        )
    }
}

private fun ScalingLazyListScope.Loading() =
    item {
        CircularProgressIndicator()
    }

private fun ScalingLazyListScope.ElapsedTime(
    elapsedTime: String,
) =
    item {
        Text(
            text = "Request elapsed time: $elapsedTime",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.caption3,
        )
    }

private fun ScalingLazyListScope.FetchedAnimals(
    uiState: UiState.AnimalsState.FetchedAnimals,
) =
    // TODO: add text "fetched animals"
    items(
        items = uiState.animals,
        key = { it.id },
    ) {
        Animal(it)
    }

private fun ScalingLazyListScope.DeletedAnimal(
    uiState: UiState.AnimalsState.DeletedAnimal,
) {
    // TODO: add text "deleted animal"
    val animal = uiState.animal
    if (animal != null) {
        item(animal.id) {
            Animal(animal)
        }
    } else {
        // TODO: add text
    }
}

private fun ScalingLazyListScope.ClearOutputButton(
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