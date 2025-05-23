package com.followme.ui.screens.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.AppRepository
import com.followme.data.entidades.Utilizador
import com.followme.ui.screens.utilizadores.utilizador.UtilizadorViewModel.UtilizadorUIState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Variável passada através do NavController que identifica o idUtilizador,
    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    /*
    Variável que vai verificar se o parâmetro passado é do tipo Inteiro.
    É necessário para quando a QUERY executada abaixo não devolver resultados (null -> crash)
    */
    private val result: Int = idUtilizador ?: 0

    /*
       Consulta à BD para obter o utilizador passado através de parâmetro
    */

    val utilizadorUIState: StateFlow<UtilizadorUIState> =
        appRepository.getUtilizador(result).map { utilizador ->
            utilizador?.toUIState() ?: UtilizadorUIState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UtilizadorUIState()
            )

    /*
  Função para fazer a conversão do objeto consulta para UIState.
  É utilizado na função apagarConsulta, mais abaixo
  */

    private fun Utilizador.toUIState(): UtilizadorUIState {
        return UtilizadorUIState(
            idUtilizador = this.idUtilizador,
            nomeUtilizador = this.nomeUtilizador
        )
    }

    // Variável que armazena o estado do objeto Consulta
    private val homeUIState = mutableStateOf(HomeUIState())

    // Variável utilizada para identificar a classe do objeto a ser impresso no log
    private val tag = HomeViewModel::class.simpleName

    /*
     objeto a ser armazenado com o displayName do utilizador
     */
    data class HomeUIState(
        var displayName: String = "",
        )


    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class HomeUIEvent {
        data class DisplayNameChanged(val displayName: String) : HomeUIEvent()
    }


    // Funcções modificadoras das variáveis
    private fun  onEvent(event: HomeUIEvent){

        //Quando o evento acontecer,

        when(event){

            // Alteração do displayName

            is HomeUIEvent.DisplayNameChanged -> {
                homeUIState.value = homeUIState.value.copy(
                    displayName = event.displayName
                )
            }
        }
    }

    /*
    Função que obtém os dados do utilizador através de consulta à API do Firebase
    Utilizada na função getDisplayName
    */
    private fun getUserData() {

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.also {
            it.displayName?.also { displayName ->
                onEvent(HomeUIEvent.DisplayNameChanged(displayName))

            }
        }
    }

    // Obter o displayName

    fun getDisplayName(): String {
        getUserData()
        return when {
            homeUIState.value.displayName.isBlank() -> {
                "nomeUtilizador"
            }

            else -> {
                homeUIState.value.displayName
            }
        }
    }


    // Função para terminar sessão no Firebse

    fun terminarSessao() {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(tag, "Logout com sucesso!")
            } else {
                Log.d(tag, "!!! Logout sem sucesso!!!. Verifique o que aconteceu!")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }


    //---------------- Navigation Drawer -----------------

    data class NavigationItem(
        val titulo: String,
        val descricao: String,
        val navegar: String,
        val icon: ImageVector
    )


    val navigationItemsList = listOf(
        NavigationItem(
            titulo = "Perfis",
            icon = Icons.Filled.AccountCircle,
            descricao = "Utilizadores",
            navegar = "Utilizadores"
        ),
        NavigationItem(
            titulo = "Definições",
            icon = Icons.Default.Settings,
            descricao = "Definições",
            navegar = "Definicoes"
        ),

        NavigationItem(
            titulo = "Informações",
            icon = Icons.Default.Info,
            descricao = "Informação da Aplicação",
            navegar = "Info"
        ),
    )
}