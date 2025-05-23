package com.followme.ui.screens.utilizadores.utilizador

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.AppRepository
import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UtilizadorViewModel(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    // Variável passada através do NavController que identifica o idUtilizador,
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    // Variável que armazena o estado do objeto Utilizador
    private val utilizadorUIState = MutableStateFlow(UtilizadorUIState())

    // Variável que armazena o estado do objeto Utilizador. Permite que seja recolhido através do metodo collectAsState no ecrã de Adicionar/Editar Utilizador
    val utilizadorUIStateFlow: StateFlow<UtilizadorUIState> = utilizadorUIState

    // Variável utilizada para identificar a classe do objeto a ser impresso no log
    private val tag = UtilizadorViewModel::class.simpleName

    /*
       Quando se edita um utilizador, o bloco if faz a consulta à BD para obter o respetivo utilizador, com o parâmetro idUtilizador, utilizando o dao definido.
     */

    init {
        if (idUtilizador != null && idUtilizador != -1) {
            viewModelScope.launch {
                appRepository.getUtilizador(idUtilizador).collect { utilizador ->
                    utilizador?.let {
                        utilizadorUIState.value = it.toUtilizadorUIState()
                    }
                }
            }
        }
    }

    /*
    Função para fazer a conversão do objeto Utilizador para UIState.
    É utilizado na função apagarUtilizador, mais abaixo
    */
    private fun Utilizador.toUtilizadorUIState(): UtilizadorUIState = UtilizadorUIState(
        idUtilizador = idUtilizador,
        nomeUtilizador = nomeUtilizador,
    )

    /*
    Define o objeto do tipo UIState
     */

    data class UtilizadorUIState(
        val idUtilizador: Int = 0,
        val nomeUtilizador: String = "",
    )

    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class UtilizadorUIEvent {
        data class IdUtilizadorChanged(val idUtilizador: Int) : UtilizadorUIEvent()
        data class NomeUtilizadorChanged(val nomeUtilizador: String) : UtilizadorUIEvent()

    }

    // Funcções modificadoras das variáveis
    fun onEvent(event: UtilizadorUIEvent) {

        //Quando o evento acontecer,
        when (event) {

            // Alteração do idUtilizador
            is UtilizadorUIEvent.IdUtilizadorChanged -> {
                utilizadorUIState.value = utilizadorUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
            }
            //Alteração do nome Utilizador
            is UtilizadorUIEvent.NomeUtilizadorChanged -> {
                utilizadorUIState.value = utilizadorUIState.value.copy(
                    nomeUtilizador = event.nomeUtilizador
                )
                printState()
            }

        }
    }

    // Imprime no log os valores do objeto utilizador para controlo
    private fun printState() {
        Log.d(tag, "Estado do Utilizador ${utilizadorUIState.value}")
    }

    /*
 Função para fazer a conversão do objeto quando se encontra em UIState para o objeto Utilizador.
 É utilizado nas funções inserirUtilizador e atualizarUtilizador mais abaixo
 */
    private fun UtilizadorUIState.toUtilizador(): Utilizador = Utilizador(
        idUtilizador = idUtilizador,
        nomeUtilizador = nomeUtilizador,

        )


    // Inserir um novo utilizador na BD
    suspend fun inserirtUtilizador() {
        appRepository.insertUtilizador(utilizadorUIState.value.toUtilizador())
    }

    // Editar um utilizador na BD
    suspend fun atualizarUtilizador() {
        appRepository.updateUtilizador(utilizadorUIState.value.toUtilizador())
    }

    // Apagar um utilizador na BD
    suspend fun apagarUtilizador(item: Utilizador) {
        //Conversão do objeto do tipo UIState que guarda a informação atual para o tipo pretendido
        utilizadorUIState.value = item.toUtilizadorUIState()

        //Chamada à BD
        appRepository.deleteUtilizador(item)
    }
}