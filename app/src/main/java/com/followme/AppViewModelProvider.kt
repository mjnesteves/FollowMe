package com.followme

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.followme.data.historicomedico.HistoricoMedicoViewModel
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.data.medicacao.MedicacaoViewModel
import com.followme.data.medicacao.medicamento.MedicamentoViewModel
import com.followme.data.utilizadores.UtilizadorViewModel
import com.followme.data.utilizadores.UtilizadoresViewModel
import com.followme.data.home.HomeViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            HomeViewModel(savedStateHandle, repository)
        }



        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            HistoricoMedicoViewModel(repository, savedStateHandle)
        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            MedicacaoViewModel(repository, savedStateHandle)
        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            ConsultaViewModel(savedStateHandle, repository)

        }


        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            MedicamentoViewModel(savedStateHandle, repository)

        }


        initializer {
            UtilizadoresViewModel(inventoryApplication().container.dataBaseRepository)

        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.dataBaseRepository
            val savedStateHandle = createSavedStateHandle()
            UtilizadorViewModel(savedStateHandle, repository)

        }


    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
