package com.followme.ui.screens.utilizadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.AppRepository
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class UtilizadoresViewModel(private val appRepository: AppRepository) : ViewModel() {

    val utilizadoresUiState: StateFlow<UtilizadoresUiState> =
        appRepository.getAllUtilizadoresStream().map { UtilizadoresUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UtilizadoresUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun apagarUtilizador(item: Utilizador) {
        appRepository.deleteUtilizador(item)
    }


}

data class UtilizadoresUiState(val usersList: List<Utilizador?> = listOf())

