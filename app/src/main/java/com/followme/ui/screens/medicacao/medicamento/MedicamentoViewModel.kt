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

    // Variável passada através do NavController que identifica o idUtilizador,
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    // Variável passada através do NavController que identifica o Medicamento,
    private val idMedicamento = savedStateHandle.get<Int>("idMedicamento")

    // Variável que armazena o estado do objeto Medicamento
    private val medicamentoUIState = MutableStateFlow(MedicamentoUIState())

    // Variável que armazena o estado do objeto Medicamento. Permite que seja recolhido através do metodo collectAsState no ecrã de Adicionar/Editar Medicamento
    val medicamentoUIStateFlow: StateFlow<MedicamentoUIState> = medicamentoUIState

    // Variável utilizada para identificar a classe do objeto a ser impresso no log
    private val tag = MedicamentoViewModel::class.simpleName

    /*
    Quando se edita um medicamento, o bloco if faz a consulta à BD para obter o respetivo medicamento, com o parâmetro idMedicamento, utilizando o dao definido.
    Quando se adiciona um medicamento, o bloco else define o idUtilizador no objeto medicamento para posterior adição à BD.
 */

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

    /*
    Função para fazer a conversão do objeto medicamento para UIState.
    É utilizado na função apagarMedicamento, mais abaixo
    */

    private fun Medicamento.toMedicamentoUIState(): MedicamentoUIState = MedicamentoUIState(
        idMedicamento = idMedicamento,
        idUtilizador = idUtilizador,
        nomeMedicamento = nomeMedicamento,
        quantidade = quantidade,
        frequencia = frequencia,
        dataFim = dataFim,
        quandoToma = quandoToma
    )


    /*
  Define o objeto do tipo UIState
   */

    data class MedicamentoUIState(
        val idMedicamento: Int = 0,
        val idUtilizador: Int = 0,
        val nomeMedicamento: String = "",
        val quantidade: Int = 0,
        val frequencia: String = "",
        val dataFim: String = "",
        val quandoToma: String = ""
    )


    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class MedicamentoUIEvent {
        data class IdMedicamentoMudou(val idMedicamento: Int) : MedicamentoUIEvent()
        data class UtilizadorMudou(val idUtilizador: Int) : MedicamentoUIEvent()
        data class NomeMedicamentoMudou(val nomeMedicamento: String) : MedicamentoUIEvent()
        data class QuantidadeMudou(val quantidade: String) : MedicamentoUIEvent()
        data class FrequenciaMudou(val frequencia: String) : MedicamentoUIEvent()
        data class DataFimMoudou(val dataFim: String) : MedicamentoUIEvent()
        data class QuandoTomaMudou(val quandoToma: String) : MedicamentoUIEvent()

    }


    // Funcções modificadoras das variáveis
    fun onEvent(event: MedicamentoUIEvent) {

        //Quando o evento acontecer,
        when (event) {

            // Alteração do idMedicamento
            is MedicamentoUIEvent.IdMedicamentoMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idMedicamento = event.idMedicamento
                )
            }

            //Alteração do idUtilizador
            is MedicamentoUIEvent.UtilizadorMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
                printState()
            }

            //Alteração do nome do medicamento
            is MedicamentoUIEvent.NomeMedicamentoMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    nomeMedicamento = event.nomeMedicamento
                )
                printState()
            }

            //Alteração da quantidade do medicamento
            is MedicamentoUIEvent.QuantidadeMudou -> {
                val parsed = event.quantidade.toIntOrNull() ?: 0
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    quantidade = parsed
                )
                printState()
            }

            //Alteração da frequencia do medicamento
            is MedicamentoUIEvent.FrequenciaMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    frequencia = event.frequencia
                )
                printState()
            }

            //Alteração da data do medicamento
            is MedicamentoUIEvent.DataFimMoudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    dataFim = event.dataFim
                )
                printState()
            }

            //Alteração do campo que define quando é a toma do medicamento
            is MedicamentoUIEvent.QuandoTomaMudou -> {
                medicamentoUIState.value = medicamentoUIState.value.copy(
                    quandoToma = event.quandoToma
                )
                printState()
            }


        }
    }

    // Imprime no log os valores do objeto medicamento para controlo
    private fun printState() {
        Log.d(tag, "Estado do  medicamento ${medicamentoUIState.value}")
    }


    /*
 Função para fazer a conversão do objeto quando se encontra em UIState para o objeto Medicamento.
 É utilizado nas funções inserirMedicamento e atualizarMedicamento mais abaixo
 */

    private fun MedicamentoUIState.toMedicamento(): Medicamento = Medicamento(
        idMedicamento = idMedicamento,
        idUtilizador = idUtilizador,
        nomeMedicamento = nomeMedicamento,
        quantidade = quantidade,
        frequencia = frequencia,
        dataFim = dataFim,
        quandoToma = quandoToma
    )

    // Inserir uma novo medicamento na BD
    suspend fun inserirtMedicamento() {
        appRepository.insertMedicamento(medicamentoUIState.value.toMedicamento())
    }

    // Atualizar o medicamento na BD
    suspend fun atualizarMedicamento() {
        appRepository.updateMedicamento(medicamentoUIState.value.toMedicamento())
    }

    // Apagar medicamento
    suspend fun apagarMedicamento(item: Medicamento) {
        medicamentoUIState.value = item.toMedicamentoUIState()
        appRepository.deleteMedicamento(item)
    }


}