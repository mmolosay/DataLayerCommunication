/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mmolosay.datalayercommunication

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import io.github.mmolosay.datalayercommunication.WearableViewModel.UiState
import io.github.mmolosay.datalayercommunication.domain.model.Animal

// region Preivews

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun MainAppPreviewEvents() {
    Application(
        uiState = previewUiState(),
        onGetAllAnimalsClick = {},
        onDeleteRandomCatClick = {},
        onClearClick = {},
    )
}

private fun previewUiState(): UiState =
    UiState(
        elapsedTime = "3569 ms",
        animals = emptyList(),
    )

// endregion

@Composable
fun Application(
    uiState: UiState,
    onGetAllAnimalsClick: () -> Unit,
    onDeleteRandomCatClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    val scalingLazyListState = rememberScalingLazyListState()
    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        timeText = { TimeText() }
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
                    text = "Get animals",
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
            items(uiState.animals) {
                Animal(it)
            }
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

@Composable
private fun Animal(animal: Animal) =
    Card(onClick = {}) {
        Text(text = "ID: ${animal.id}")
        Text(text = "Species: ${animal.species}")
        Text(text = "Name: ${animal.name}")
        Text(text = "Age: ${animal.age}")
    }
