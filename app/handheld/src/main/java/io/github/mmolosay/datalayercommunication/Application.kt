package io.github.mmolosay.datalayercommunication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// region Previews

@Preview
@Composable
fun MainAppPreview() {
    Application()
}

// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Application() {
    Scaffold { padding ->
        Content(
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) =
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "No functionality here.",
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Data being sent by watch is intercepted by service in background.",
            textAlign = TextAlign.Center,
        )
    }
