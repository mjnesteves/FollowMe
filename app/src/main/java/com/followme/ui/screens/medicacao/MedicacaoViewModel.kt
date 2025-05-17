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

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val result: Int = idUtilizador ?: 0

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

    val tag = MedicamentoViewModel::class.simpleName

    suspend fun apagarMedicamento(item: Medicamento) {

        Log.d(tag, "APAGAR MEDICAMENTO {${item.idMedicamento}")

        appRepository.deleteMedicamento(item)
    }


}

data class MedicacaoUiState(val medicamentoList: List<Medicamento?> = listOf())