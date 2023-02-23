package io.github.mmolosay.datalayercommunication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text

// region Previews

@Preview
@Composable
private fun ConnectionFailurePreview() =
    ConnectionFailure(
        onTryAgainClick = {},
    )

// endregion

@Composable
fun ConnectionFailure(
    onTryAgainClick: () -> Unit,
) =
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Watch is not connected to handheld device.",
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onTryAgainClick,
            colors = ButtonDefaults.secondaryButtonColors(),
        ) {
            Text(
                text = "Try again",
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
    }
