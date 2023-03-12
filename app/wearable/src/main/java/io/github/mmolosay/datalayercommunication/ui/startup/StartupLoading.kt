package io.github.mmolosay.datalayercommunication.ui.startup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.CircularProgressIndicator

// region Previews

@Preview
@Composable
private fun StartupLoadingPreview() =
    StartupLoading()

// endregion

@Composable
fun StartupLoading() =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }