package com.followme.ui.screens.historico_medico

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.entidades.Consulta
import com.followme.data.AppRepository
import com.followme.ui.screens.medicacao.medicamento.MedicamentoViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoricoMedicoViewModel(
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val result: Int = idUtilizador ?: 0

    val historicoMedicoUiState: StateFlow<HistoricoMedicoUiState> =
        appRepository.getAllConsultasUser(result).map { HistoricoMedicoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HistoricoMedicoUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val tag = MedicamentoViewModel::class.simpleName

    suspend fun apagarConsulta(item: Consulta) {
        Log.d(tag, "APAGAR CONSULTA ${item.idConsulta}")
        appRepository.deleteConsulta(item)
    }

}

data class HistoricoMedicoUiState(val consultaList: List<Consulta?> = listOf())


