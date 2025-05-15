package com.followme.data.historicomedico.consulta


import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.Consulta

import com.followme.data.dataBase.DataBaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ConsultaViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    private val idConsulta = savedStateHandle.get<Int>("idConsulta")
    private val consultaUIState = MutableStateFlow(ConsultaUIState())
    val consultaUIStateFlow: StateFlow<ConsultaUIState> = consultaUIState

    val tag = ConsultaViewModel::class.simpleName


    init {
        if (idConsulta != null && idConsulta != -1) {
            // Edit existing consulta
            viewModelScope.launch {
                dataBaseRepository.getConsultaStream(idConsulta).collect { consulta ->
                    consulta?.let {
                        consultaUIState.value = it.toConsultaUIState()
                    }
                }
            }
        } else {
            // New consulta — manually set idUtilizador from SavedStateHandle
            consultaUIState.value = idUtilizador?.let {
                consultaUIState.value.copy(
                    idUtilizador = it
                )
            }!!

        }
    }


    private fun Consulta.toConsultaUIState(): ConsultaUIState = ConsultaUIState(
        idConsulta = idConsulta,
        idUtilizador = idUtilizador,
        especialidade = especialidade,
        hospital = hospital,
        horaConsulta = horaConsulta,
        dataConsulta = dataConsulta
    )


    data class ConsultaUIState(
        var idConsulta: Int = 0,
        var idUtilizador: Int = 0,
        val especialidade: String = "",
        val hospital: String = "",
        val horaConsulta: String = "",
        val dataConsulta: String = "",
    )

    sealed class ConsultaUIEvent {

        data class IdConsultaChanged(val idConsulta: Int) : ConsultaUIEvent()
        data class UtilizadorChanged(val idUtilizador: Int) : ConsultaUIEvent()
        data class EspecialidadeChanged(val especialidade: String) : ConsultaUIEvent()
        data class HospitalChanged(val hospital: String) : ConsultaUIEvent()
        data class HoraConsultaChanged(val horaConsulta: String) : ConsultaUIEvent()
        data class DataConsultaChanged(val dataConsulta: String) : ConsultaUIEvent()

    }





    fun onEvent(event: ConsultaUIEvent) {

        when (event) {

            is ConsultaUIEvent.IdConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    idConsulta = event.idConsulta
                )
            }
            is ConsultaUIEvent.UtilizadorChanged -> {
                //val parsed = event.idUtilizador.toIntOrNull() ?: 0
                consultaUIState.value = consultaUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
                printState()
            }

            is ConsultaUIEvent.EspecialidadeChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    especialidade = event.especialidade
                )
                printState()
            }

            is ConsultaUIEvent.HospitalChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    hospital = event.hospital
                )
                printState()
            }
            is ConsultaUIEvent.HoraConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    horaConsulta = event.horaConsulta
                )
                printState()
            }

            is ConsultaUIEvent.DataConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    dataConsulta = event.dataConsulta
                )
                printState()
            }
        }
    }


    private fun printState() {
        Log.d(tag, "PRINT STATE")
        Log.d(tag, consultaUIState.value.toString())
    }


    private fun ConsultaUIState.toConsulta(): Consulta = Consulta(
        idConsulta = idConsulta,
        idUtilizador = idUtilizador,
        especialidade = especialidade,
        hospital = hospital,
        horaConsulta = horaConsulta,
        dataConsulta = dataConsulta
    )


    suspend fun insertConsulta() {
        dataBaseRepository.insertConsulta(consultaUIState.value.toConsulta())
    }

    suspend fun updateConsulta() {
        dataBaseRepository.updateConsulta(consultaUIState.value.toConsulta())
    }


}


/*
    val consulta = Consulta(
        idUtilizador = consultasUIState.value.idUtilizador,
        especialidade =  consultasUIState.value.especialidade,
        hospital =  consultasUIState.value.hospital,
        dataConsulta = consultasUIState.value.dataConsulta
    )


}



fun ConsultaDetails.toConsulta(): Consulta = Consulta(
    idConsulta=  idConsulta,
    idUtilizador = idUtilizador,
    especialidade = especialidade,
    hospital = hospital,
    dataConsulta = dataConsulta,
)

fun Consulta.toConsultaUiState(isEntryValid: Boolean = false): ConsultaUiState = ConsultaUiState(
    consultaDetails = this.toConsultaDetails(),
)

private fun Consulta.toConsultaDetails(): ConsultaDetails = ConsultaDetails(
    idConsulta=  idConsulta,
    idUtilizador = idUtilizador,
    especialidade = especialidade,
    hospital = hospital,
    dataConsulta = dataConsulta,
)

private val tag = ConsultaViewModel::class.simpleName

private fun validateInput(uiState: ConsultaDetails = consultaUiState.consultaDetails): Boolean {

    return with(uiState) {
        Log.d(tag, "$especialidade, $hospital, $dataConsulta")
        return especialidade.isNotBlank() && hospital.isNotBlank()

    }


}
*/