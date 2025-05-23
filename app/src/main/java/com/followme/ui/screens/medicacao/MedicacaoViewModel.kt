package com.followme.ui.screens.medicacao

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.followme.data.AppRepository
import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Medicamento
import com.followme.ui.screens.historico_medico.consulta.ConsultaViewModel
import com.followme.ui.screens.medicacao.medicamento.MedicamentoViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MedicacaoViewModel(
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Variável passada através de parâmetro, através do NavController, para identificar o utilizador para o qual, vai ser exibido o histórico de medicamentos
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    /*
    Variável que vai verificar se o parâmetro passado é do tipo Inteiro.
    É necessário para quando a QUERY executada abaixo não devolver resultados (null -> crash)
    */
    private val result: Int = idUtilizador ?: 0


    /*
Consulta à BD para obter todos os medicamentos do utilizador fornecido
 */
    val medicacaoUiState: StateFlow<MedicacaoUiState> =
        appRepository.getAllMedicamentosUser(result).map { MedicacaoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MedicacaoUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}


// Objeto que vai guardar a lista de todos os medicamentos relacionadas com o utilizador
data class MedicacaoUiState(val medicamentoList: List<Medicamento?> = listOf())