package com.followme.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.DataBaseRepository
import com.followme.data.dataBase.Utilizador
import com.followme.data.utilizadores.UtilizadorViewModel
import com.followme.data.utilizadores.UtilizadorViewModel.UtilizadorUIState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")

    private val result: Int = idUtilizador ?: 0

    val utilizadorUIStateFlow: StateFlow<UtilizadorUIState> =
        dataBaseRepository.getUtilizadorStream(result)
            .map { utilizador ->
                utilizador?.toUIState() ?: UtilizadorUIState() // fallback if null
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

    fun getNomeUtilizador(): String {
        return utilizadorUIStateFlow.value.nomeUtilizador
    }


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




    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Perfis",
            icon = Icons.Filled.AccountCircle,
            description = "Utilizadores",
            navigateTo = "Utilizadores"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            navigateTo = "SettingsScreen"
        ),

        NavigationItem(
            title = "Info",
            icon = Icons.Default.Info,
            description = "Information Screen",
            navigateTo = "AdicionarConsulta"
        ),


        )


    fun terminarSessao() {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(tag, "Inside signout sucess")
                onEvent(HomeUIEvent.DisplayNameChanged(""))
            } else {
                Log.d(tag, "Inside signout is not complete")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }


}