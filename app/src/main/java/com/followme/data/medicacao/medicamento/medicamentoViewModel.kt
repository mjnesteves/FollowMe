package com.followme.data.medicacao.medicamento

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.DataBaseRepository
import com.followme.data.dataBase.Medicamento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicamentoViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    private val idMedicamento = savedStateHandle.get<Int>("idMedicamento")
    private val medicamentoUIState = MutableStateFlow(MedicamentoUIState())
    val medicamentoUIStateFlow: StateFlow<MedicamentoUIState> = medicamentoUIState


    init {
        if (idMedicamento != null && idMedicamento != -1) {
            viewModelScope.launch {
                dataBaseRepository.getMedicamentoStream(idMedicamento).collect { medicamento ->
                    medicamento?.let {
                        medicamentoUIState.value = it.toMedicamentoUIState()
                    }
                }
            }
        } else {
            // New consulta — manually set idUtilizador from SavedStateHandle
            medicamentoUIState.value = idUtilizador?.let {
                medicamentoUIState.value.copy(
                    idUtilizador = it
                )
            }!!

        }

    }


    private fun Medicamento.toMedicamentoUIState(): MedicamentoUIState = MedicamentoUIState(
        idMedicamento = idMedicamento,
        idUtilizador = idUtilizador,
        nomeMedicamento = nomeMedicamento,
        quantidade = quantidade,
        frequencia = frequencia,
        dataFim = dataFim,
        quandoToma = quandoToma
    )


    private val tag = MedicamentoViewModel::class.simpleName

    data class MedicamentoUIState(
        val idMedicamento: Int = 0,
        val idUtilizador: Int = 0,
        val nomeMedicamento: String = "",
        val quantidade: Int = 0,
        val frequencia: String = "",
        val dataFim: String = "",
        val quandoToma: String = ""
    )

    sealed class MedicamentoUIEvent {

        data class IdMedicamentoChanged(val idMedicamento: Int) : MedicamentoUIEvent()
        data class UtilizadorChanged(val idUtilizador: Int) : MedicamentoUIEvent()
        data class NomeMedicamentoChanged(val nomeMedicamento: String) : MedicamentoUIEvent()
        data class QuantidadeChanged(val quantidade: String) : MedicamentoUIEvent()
        data class FrequenciaChanged(val frequencia: String) : MedicamentoUIEvent()
        data class DataFimChanged(val dataFim: String) : MedicamentoUIEvent()
        data class QuandoTomaChanged(val quandoToma: String) : MedicamentoUIEvent()

    }


    fun onEvent(event: MedicamentoUIEvent) {

        when (event) {

            is MedicamentoUIEvent.IdMedicamentoChanged -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idMedicamento = event.idMedicamento
                )
            }

            is MedicamentoUIEvent.UtilizadorChanged -> {
                //val parsed = event.idUtilizador.toIntOrNull() ?: 0
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
                printState()
            }

            is MedicamentoUIEvent.NomeMedicamentoChanged -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    nomeMedicamento = event.nomeMedicamento
                )
                printState()
            }

            is MedicamentoUIEvent.QuantidadeChanged -> {
                val parsed = event.quantidade.toIntOrNull() ?: 0
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    quantidade = parsed
                )
                printState()
            }

            is MedicamentoUIEvent.FrequenciaChanged -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    frequencia = event.frequencia
                )
                printState()
            }

            is MedicamentoUIEvent.DataFimChanged -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    dataFim = event.dataFim
                )
                printState()
            }

            is MedicamentoUIEvent.QuandoTomaChanged -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    quandoToma = event.quandoToma
                )
                printState()
            }


        }
    }


    private fun printState() {
        Log.d(tag, "PRINT STATE")
        Log.d(tag, medicamentoUIState.value.toString())
    }


    private fun MedicamentoUIState.toMedicamento(): Medicamento = Medicamento(
        idMedicamento = idMedicamento,
        idUtilizador = idUtilizador,
        nomeMedicamento = nomeMedicamento,
        quantidade = quantidade,
        frequencia = frequencia,
        dataFim = dataFim,
        quandoToma = quandoToma
    )


    suspend fun insertMedicamento() {
        dataBaseRepository.insertMedicamento(medicamentoUIState.value.toMedicamento())
    }

    suspend fun updateMedicamento() {
        dataBaseRepository.updateMedicamento(medicamentoUIState.value.toMedicamento())
    }


}