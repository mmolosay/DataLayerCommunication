package io.github.mmolosay.datalayercommunication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.viewmodel.WearableViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WearableViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            Application(
                uiState = uiState,
                onGetAllAnimalsClick = ::onGetAnimalsClick,
                onDeleteRandomCatClick = ::onDeleteRandomCatClick,
                onClearOutputClick = ::onClearOutputClick,
                onConnectionFailureTryAgainClick = ::onConnectionFailureTryAgainClick,
            )
        }
    }

    private fun onGetAnimalsClick() {
        viewModel.executeGetAllAnimals()
    }

    private fun onDeleteRandomCatClick() {
        viewModel.executeDeleteRandomAnimal(ofSpecies = Animal.Species.Cat)
    }

    private fun onClearOutputClick() {
        viewModel.clearOutput()
    }

    private fun onConnectionFailureTryAgainClick() {
        viewModel.restartHandheldConnectionCheck()
    }
}
