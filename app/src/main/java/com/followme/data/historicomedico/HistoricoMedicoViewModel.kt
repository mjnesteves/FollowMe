package com.followme.data.historicomedico

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.Consulta
import com.followme.data.dataBase.DataBaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoricoMedicoViewModel(
    private val dataBaseRepository: DataBaseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val result: Int = idUtilizador ?: 0

    val historicoMedicoUiState: StateFlow<HistoricoMedicoUiState> =
        dataBaseRepository.getAllConsultasUser(result).map { HistoricoMedicoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HistoricoMedicoUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    suspend fun apagarConsulta(item: Consulta) {
        dataBaseRepository.deleteConsulta(item)
    }



}

data class HistoricoMedicoUiState(val consultaList: List<Consulta?> = listOf())


