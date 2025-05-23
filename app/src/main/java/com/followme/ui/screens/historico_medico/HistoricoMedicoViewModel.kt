package com.followme.ui.screens.historico_medico

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.entidades.Consulta
import com.followme.data.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoricoMedicoViewModel(
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    // Variável passada através de parâmetro, através do NavController, para identificar o utilizador para o qual, vai ser exibido o histórico de consultas
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    /*
    Variável que vai verificar se o parâmetro passado é do tipo Inteiro.
    É necessário para quando a QUERY executada abaixo não devolver resultados (null -> crash)
     */
    private val result: Int = idUtilizador ?: 0


    /*
    Consulta à BD para obter todas as consultas do utilizador fornecido
     */
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
}


// Objeto que vai guardar a lista de todas as consultas relacionadas com o utilizador
data class HistoricoMedicoUiState(val consultaList: List<Consulta?> = listOf())


