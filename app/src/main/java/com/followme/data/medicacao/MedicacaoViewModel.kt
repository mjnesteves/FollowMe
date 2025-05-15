package com.followme.data.medicacao

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


import com.followme.data.dataBase.DataBaseRepository
import com.followme.data.dataBase.Medicamento
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class MedicacaoViewModel(
    private val dataBaseRepository: DataBaseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val result: Int = idUtilizador ?: 0

    val medicacaoUiState: StateFlow<MedicacaoUiState> =
        dataBaseRepository.getAllMedicamentosUser(result).map { MedicacaoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MedicacaoUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun apagarMedicamento(item: Medicamento) {
        dataBaseRepository.deleteMedicamento(item)
    }

}

data class MedicacaoUiState(val medicamentoList: List<Medicamento?> = listOf())