package io.github.mmolosay.datalayercommunication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.viewmodel.WearableViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WearableViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState = viewModel.uiState
            Application(
                uiState = uiState,
                onGetAllAnimalsClick = ::onGetAnimalsClick,
                onDeleteRandomCatClick = ::onDeleteRandomCatClick,
                onClearClick = ::onClearClick,
                onConnectionFailureTryAgainClick = ::onConnectionFailureTryAgainClick,
            )
        }
    }

    private fun onGetAnimalsClick() {
        viewModel.getAllAnimals()
    }

    private fun onDeleteRandomCatClick() {
        viewModel.deleteRandomAnimal(ofSpecies = Animal.Species.Cat)
    }

    private fun onClearClick() {
        viewModel.clearOutput()
    }

    private fun onConnectionFailureTryAgainClick() {
        viewModel.launchConnectionCheck()
    }
}
