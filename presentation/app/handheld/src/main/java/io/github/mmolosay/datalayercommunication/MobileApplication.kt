package io.github.mmolosay.datalayercommunication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
        Box(
            modifier = Modifier.padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "No functionality here.\nMessages being sent from watch are intercepted in service.")
        }
    }
}
