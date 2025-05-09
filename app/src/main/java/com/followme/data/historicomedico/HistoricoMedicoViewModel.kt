package com.followme.data.historicomedico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.Consulta
import com.followme.data.dataBase.DataBaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoricoMedicoViewModel(dataBaseRepository: DataBaseRepository) : ViewModel() {

    val historicoMedicoUiState: StateFlow<HistoricoMedicoUiState> =
        dataBaseRepository.getAllConsultasStream().map { HistoricoMedicoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HistoricoMedicoUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class HistoricoMedicoUiState(val consultaList: List<Consulta> = listOf())