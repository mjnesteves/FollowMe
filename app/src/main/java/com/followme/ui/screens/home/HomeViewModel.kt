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
    private val appRepository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val result: Int = idUtilizador ?: 0

    val utilizadorUIState: StateFlow<UtilizadorUIState> =
        appRepository.getUtilizador(result).map { utilizador ->
            utilizador?.toUIState() ?: UtilizadorUIState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UtilizadorUIState()
            )

    private fun Utilizador.toUIState(): UtilizadorUIState {
        return UtilizadorUIState(
            idUtilizador = this.idUtilizador,
            nomeUtilizador = this.nomeUtilizador
        )
    }

    private val homeUIState = mutableStateOf(HomeUIState())

    private val tag = HomeViewModel::class.simpleName


    data class HomeUIState(
        var displayName: String = "",
        )


    sealed class HomeUIEvent {
        data class DisplayNameChanged(val displayName: String) : HomeUIEvent()
    }



    private fun  onEvent(event: HomeUIEvent){
        when(event){
            is HomeUIEvent.DisplayNameChanged -> {
                homeUIState.value = homeUIState.value.copy(
                    displayName = event.displayName
                )
            }

        }

    }


    private fun getUserData() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.also {
            it.displayName?.also { displayName ->
                onEvent(HomeUIEvent.DisplayNameChanged(displayName))

            }
        }

    }


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


    data class NavigationItem(
        val title: String,
        val description: String,
        val navigateTo: String,
        val icon: ImageVector
    )


    val navigationItemsList = listOf(
        NavigationItem(
            title = "Perfis",
            icon = Icons.Filled.AccountCircle,
            description = "Utilizadores",
            navigateTo = "Utilizadores"
        ),
        NavigationItem(
            title = "Definições",
            icon = Icons.Default.Settings,
            description = "Definições",
            navigateTo = "Definicoes"
        ),

        NavigationItem(
            title = "Informações",
            icon = Icons.Default.Info,
            description = "Informação da Aplicação",
            navigateTo = "Info"
        ),


        )


    fun terminarSessao() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(tag, "Logout com sucesso!")
                //onEvent(HomeUIEvent.DisplayNameChanged(""))
            } else {
                Log.d(tag, "Logout sem sucesso!. Verifique o que aconteceu!")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }


}