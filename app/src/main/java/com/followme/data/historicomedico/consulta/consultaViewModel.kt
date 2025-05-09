package com.followme.data.historicomedico.consulta


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.followme.data.criarconta.CriarContaViewModel.CriarContaUIState
import com.followme.data.dataBase.Consulta

import com.followme.data.dataBase.DataBaseRepository


class ConsultaViewModel(private val dataBaseRepository: DataBaseRepository) : ViewModel() {

    private val tag = ConsultaViewModel::class.simpleName

    data class ConsultaUIState(
        val idConsulta: Int = 0,
        val idUtilizador: Int = 0,
        val especialidade: String = "",
        val hospital: String = "",
        val dataConsulta: String = "",
    )

    sealed class ConsultaUIEvent {

        data class UtilizadorChanged(val idUtilizador: Int) : ConsultaUIEvent()
        data class EspecialidadeChanged(val especialidade: String) : ConsultaUIEvent()
        data class HospitalChanged(val hospital: String) : ConsultaUIEvent()
        data class DataConsultaChanged(val dataConsulta: String) : ConsultaUIEvent()

    }


    private var consultaUIState = mutableStateOf(ConsultaUIState())


    fun onEvent(event: ConsultaUIEvent) {

        when (event) {
            is ConsultaUIEvent.UtilizadorChanged -> {
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
        dataConsulta = dataConsulta,
    )


    suspend fun saveItem() {
        dataBaseRepository.insertConsulta(consultaUIState.value.toConsulta())
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