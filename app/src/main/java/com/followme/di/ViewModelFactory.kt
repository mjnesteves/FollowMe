package com.followme.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.followme.ui.screens.autenticacao.criarconta.CriarContaViewModel
import com.followme.ui.screens.autenticacao.login.LoginViewModel

import com.followme.ui.screens.historico_medico.HistoricoMedicoViewModel
import com.followme.ui.screens.historico_medico.consulta.ConsultaViewModel
import com.followme.ui.screens.medicacao.MedicacaoViewModel
import com.followme.ui.screens.medicacao.medicamento.MedicamentoViewModel
import com.followme.ui.screens.utilizadores.utilizador.UtilizadorViewModel
import com.followme.ui.screens.utilizadores.UtilizadoresViewModel
import com.followme.ui.screens.home.HomeViewModel

/*
ViewModel Factory
 */


object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Inicia os ViewModels

        initializer {
            CriarContaViewModel(inventoryApplication().container.repositorio)
        }

        initializer {
            LoginViewModel(inventoryApplication().container.repositorio)
        }


        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            HomeViewModel(repository, savedStateHandle)
        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            HistoricoMedicoViewModel(repository, savedStateHandle)
        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            MedicacaoViewModel(repository, savedStateHandle)
        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            ConsultaViewModel(savedStateHandle, repository)

        }


        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            MedicamentoViewModel(savedStateHandle, repository)

        }


        initializer {
            UtilizadoresViewModel(inventoryApplication().container.repositorio)

        }

        initializer {
            val application = inventoryApplication()
            val repository = application.container.repositorio
            val savedStateHandle = createSavedStateHandle()
            UtilizadorViewModel(savedStateHandle, repository)

        }


    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [AppInstanceLaucher].
 */
fun CreationExtras.inventoryApplication(): AppInstanceLaucher =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AppInstanceLaucher)
