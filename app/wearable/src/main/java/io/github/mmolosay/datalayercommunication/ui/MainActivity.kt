package io.github.mmolosay.datalayercommunication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import io.github.mmolosay.datalayercommunication.viewmodel.WearableViewModel
import io.github.mmolosay.datalayercommunication.domain.model.Animal

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
                onGetCatsOlderThan1Click = ::onGetCatsOlderThan1Click,
                onDeleteRandomCatClick = ::onDeleteRandomCatClick,
                onClearClick = ::onClearClick,
            )
        }
    }

    private fun onGetAnimalsClick() {
        viewModel.getAllAnimals()
    }

    private fun onGetCatsOlderThan1Click() {
        viewModel.getCatsOlderThan1()
    }

    private fun onDeleteRandomCatClick() {
        viewModel.deleteRandomAnimal(ofSpecies = Animal.Species.Cat)
    }

    private fun onClearClick() {
        viewModel.clearAnimals()
    }
}
