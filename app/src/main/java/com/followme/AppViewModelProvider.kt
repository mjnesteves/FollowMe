package com.followme

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.followme.data.historicomedico.HistoricoMedicoViewModel
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.data.medicacao.MedicacaoViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {

        /*
        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }


         */


        // Initializer for HomeViewModel
        initializer {
            HistoricoMedicoViewModel(inventoryApplication().container.dataBaseRepository)

        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            ConsultaViewModel(repository)

        }


        initializer {
            MedicacaoViewModel(inventoryApplication().container.dataBaseRepository)

        }


    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
