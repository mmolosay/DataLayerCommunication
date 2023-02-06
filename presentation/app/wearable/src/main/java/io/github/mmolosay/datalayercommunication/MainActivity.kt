package io.github.mmolosay.datalayercommunication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<WearableViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by viewModel.uiState
            Application(
                uiState = uiState,
                onGetAllAnimalsClick = ::onGetAnimalsClick,
                onDeleteRandomCatClick = ::onDeleteRandomCatClick,
                onClearClick = ::onClearClick,
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
        viewModel.clearAnimals()
    }
}
