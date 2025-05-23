package com.followme.ui.screens.historico_medico.consulta


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.entidades.Consulta
import com.followme.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class ConsultaViewModel(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    // Variável passada através do NavController que identifica o idUtilizador,
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    // Variável passada através do NavController que identifica o idConsulta,
    private val idConsulta = savedStateHandle.get<Int>("idConsulta")

    // Variável que armazena o estado do objeto Consulta
    private val consultaUIState = MutableStateFlow(ConsultaUIState())

    // Variável que armazena o estado do objeto Consulta. Permite que seja recolhido através do metodo collectAsState no ecrã de Adicionar/Editar Consulta
    val consultaUIStateFlow: StateFlow<ConsultaUIState> = consultaUIState

    // Variável utilizada para identificar a classe do objeto a ser impresso no log
    val tag = ConsultaViewModel::class.simpleName


    /*
    Quando se edita uma consulta, o bloco if faz a consulta à BD para obter a respetiva consulta, com o parâmetro idConsulta, utilizando o dao definido.
    Quando se adiciona uma consulta, o bloco else define o idUtilizador no objeto consulta para posterior adição à BD.
     */
    init {
        if (idConsulta != null && idConsulta != -1) {
            viewModelScope.launch {
                appRepository.getConsulta(idConsulta)
                    .filterNotNull()
                    .collect { consulta ->
                        consulta.let {
                        consultaUIState.value = it.toConsultaUIState()
                    }
                }
            }
        } else {
            consultaUIState.value = idUtilizador?.let {
                consultaUIState.value.copy(
                    idUtilizador = it
                )
            }!!
        }
    }

    /*
    Função para fazer a conversão do objeto consulta para UIState.
    É utilizado na função apagarConsulta, mais abaixo
    */
    private fun Consulta.toConsultaUIState(): ConsultaUIState = ConsultaUIState(
        idConsulta = idConsulta,
        idUtilizador = idUtilizador,
        especialidade = especialidade,
        hospital = hospital,
        horaConsulta = horaConsulta,
        dataConsulta = dataConsulta
    )

    /*
    Define o objeto do tipo UIState
     */

    data class ConsultaUIState(
        var idConsulta: Int = 0,
        var idUtilizador: Int = 0,
        val especialidade: String = "",
        val hospital: String = "",
        val horaConsulta: String = "",
        val dataConsulta: String = "",
    )


    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class ConsultaUIEvent {
        data class IdConsultaChanged(val idConsulta: Int) : ConsultaUIEvent()
        data class UtilizadorChanged(val idUtilizador: Int) : ConsultaUIEvent()
        data class EspecialidadeChanged(val especialidade: String) : ConsultaUIEvent()
        data class HospitalChanged(val hospital: String) : ConsultaUIEvent()
        data class HoraConsultaChanged(val horaConsulta: String) : ConsultaUIEvent()
        data class DataConsultaChanged(val dataConsulta: String) : ConsultaUIEvent()

    }


    // Funcções modificadoras das variáveis
    fun onEvent(event: ConsultaUIEvent) {

        //Quando o evento acontecer,
        when (event) {

            // Alteração do idConsulta
            is ConsultaUIEvent.IdConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    idConsulta = event.idConsulta
                )
            }

            //Alteração do idUtilizador
            is ConsultaUIEvent.UtilizadorChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
                printState()
            }

            //Alteração da especialidade
            is ConsultaUIEvent.EspecialidadeChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    especialidade = event.especialidade
                )
                printState()
            }

            //Alteração do Hospital
            is ConsultaUIEvent.HospitalChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    hospital = event.hospital
                )
                printState()
            }


            //Alteração da Hora da Consulta
            is ConsultaUIEvent.HoraConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    horaConsulta = event.horaConsulta
                )
                printState()
            }

            //Alteração da data da Consulta
            is ConsultaUIEvent.DataConsultaChanged -> {
                consultaUIState.value = consultaUIState.value.copy(
                    dataConsulta = event.dataConsulta
                )
                printState()
            }
        }
    }


    // Imprime no log os valores do objeto consulta para controlo

    private fun printState() {
        Log.d(tag, "Estado da Consulta ${consultaUIState.value}")
    }

    /*
     Função para fazer a conversão do objeto quando se encontra em UIState para o objeto Consulta.
     É utilizado nas funções inserirConsulta e atualizarConsulta mais abaixo
     */

    private fun ConsultaUIState.toConsulta(): Consulta = Consulta(
        idConsulta = idConsulta,
        idUtilizador = idUtilizador,
        especialidade = especialidade,
        hospital = hospital,
        horaConsulta = horaConsulta,
        dataConsulta = dataConsulta
    )


    // Inserir uma nova consulta na BD
    suspend fun inserirConsulta() {
        appRepository.insertConsulta(consultaUIState.value.toConsulta())
    }

    // Atualizar a consulta na BD
    suspend fun atualizarConsulta() {
        appRepository.updateConsulta(consultaUIState.value.toConsulta())
    }

    // Apagar Consulta
    suspend fun apagarConsulta(item: Consulta) {
        //Conversão do objeto do tipo UIState que guarda a informação atual para o tipo pretendido
        consultaUIState.value = item.toConsultaUIState()

        //Chamada à BD
        appRepository.deleteConsulta(item)
    }


}

