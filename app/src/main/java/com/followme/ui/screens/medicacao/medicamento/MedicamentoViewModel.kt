package com.followme.ui.screens.medicacao.medicamento

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.AppRepository
import com.followme.data.entidades.Medicamento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicamentoViewModel(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {


    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val idMedicamento = savedStateHandle.get<Int>("idMedicamento")

    private val medicamentoUIState = MutableStateFlow(MedicamentoUIState())
    val medicamentoUIStateFlow: StateFlow<MedicamentoUIState> = medicamentoUIState


    init {
        if (idMedicamento != null && idMedicamento != -1) {
            viewModelScope.launch {
                appRepository.getMedicamento(idMedicamento).collect { medicamento ->
                    medicamento?.let {
                        medicamentoUIState.value = it.toMedicamentoUIState()
                    }
                }
            }
        } else {
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

        data class IdMedicamentoMudou(val idMedicamento: Int) : MedicamentoUIEvent()
        data class UtilizadorMudou(val idUtilizador: Int) : MedicamentoUIEvent()
        data class NomeMedicamentoMudou(val nomeMedicamento: String) : MedicamentoUIEvent()
        data class QuantidadeMudou(val quantidade: String) : MedicamentoUIEvent()
        data class FrequenciaMudou(val frequencia: String) : MedicamentoUIEvent()
        data class DataFimMoudou(val dataFim: String) : MedicamentoUIEvent()
        data class QuandoTomaMudou(val quandoToma: String) : MedicamentoUIEvent()

    }


    fun onEvent(event: MedicamentoUIEvent) {

        when (event) {

            is MedicamentoUIEvent.IdMedicamentoMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idMedicamento = event.idMedicamento
                )
            }

            is MedicamentoUIEvent.UtilizadorMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
                printState()
            }

            is MedicamentoUIEvent.NomeMedicamentoMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    nomeMedicamento = event.nomeMedicamento
                )
                printState()
            }

            is MedicamentoUIEvent.QuantidadeMudou -> {
                val parsed = event.quantidade.toIntOrNull() ?: 0
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    quantidade = parsed
                )
                printState()
            }

            is MedicamentoUIEvent.FrequenciaMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    frequencia = event.frequencia
                )
                printState()
            }

            is MedicamentoUIEvent.DataFimMoudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    dataFim = event.dataFim
                )
                printState()
            }

            is MedicamentoUIEvent.QuandoTomaMudou -> {
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
        appRepository.insertMedicamento(medicamentoUIState.value.toMedicamento())
    }

    suspend fun updateMedicamento() {
        appRepository.updateMedicamento(medicamentoUIState.value.toMedicamento())
    }

    suspend fun apagarMedicamento(item: Medicamento) {
        medicamentoUIState.value = item.toMedicamentoUIState()
        appRepository.deleteMedicamento(item)
    }


}