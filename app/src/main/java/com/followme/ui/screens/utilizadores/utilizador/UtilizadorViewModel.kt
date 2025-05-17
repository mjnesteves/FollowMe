package com.followme.ui.screens.utilizadores.utilizador

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.AppRepository
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UtilizadorViewModel(
    savedStateHandle: SavedStateHandle,
    private val appRepository: AppRepository
) : ViewModel() {

    private val idUtilizador = savedStateHandle.get<Int>("idUtilizador")
    private val utilizadorUIState = MutableStateFlow(UtilizadorUIState())

    val utilizadorUIStateFlow: StateFlow<UtilizadorUIState> = utilizadorUIState

    init {
        if (idUtilizador != null && idUtilizador != -1) {
            viewModelScope.launch {
                appRepository.getUtilizadorStream(idUtilizador).collect { utilizador ->
                    utilizador?.let {
                        utilizadorUIState.value = it.toUtilizadorUIState()
                    }
                }
            }
        }
    }


    private fun Utilizador.toUtilizadorUIState(): UtilizadorUIState = UtilizadorUIState(
        idUtilizador = idUtilizador,
        nomeUtilizador = nomeUtilizador,
    )


    private val tag = UtilizadorViewModel::class.simpleName

    data class UtilizadorUIState(
        val idUtilizador: Int = 0,
        val nomeUtilizador: String = "",
    )

    sealed class UtilizadorUIEvent {
        data class IdUtilizadorChanged(val idUtilizador: Int) : UtilizadorUIEvent()
        data class NomeUtilizadorChanged(val nomeUtilizador: String) : UtilizadorUIEvent()

    }


    fun onEvent(event: UtilizadorUIEvent) {

        when (event) {

            is UtilizadorUIEvent.IdUtilizadorChanged -> {
                utilizadorUIState.value = utilizadorUIState.value.copy(
                    idUtilizador = event.idUtilizador
                )
            }

            is UtilizadorUIEvent.NomeUtilizadorChanged -> {
                utilizadorUIState.value = utilizadorUIState.value.copy(
                    nomeUtilizador = event.nomeUtilizador
                )
                printState()
            }

        }
    }


    private fun printState() {
        Log.d(tag, "PRINT STATE")
        Log.d(tag, utilizadorUIState.value.toString())
    }


    fun UtilizadorUIState.toUtilizador(): Utilizador = Utilizador(
        idUtilizador = idUtilizador,
        nomeUtilizador = nomeUtilizador,

        )


    suspend fun insertUtilizador() {
        appRepository.insertUtilizador(utilizadorUIState.value.toUtilizador())
    }

    suspend fun updateUtilizador() {
        appRepository.updateUtilizador(utilizadorUIState.value.toUtilizador())
    }


}