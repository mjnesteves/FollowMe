package com.followme.data.utilizadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.followme.data.dataBase.DataBaseRepository
import com.followme.data.dataBase.Utilizador
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class UtilizadoresViewModel(private val dataBaseRepository: DataBaseRepository) : ViewModel() {

    val utilizadoresUiState: StateFlow<UtilizadoresUiState> =
        dataBaseRepository.getAllUtilizadoresStream().map { UtilizadoresUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UtilizadoresUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun apagarUtilizador(item: Utilizador) {
        dataBaseRepository.deleteUtilizador(item)
    }

    fun getUtilizadorStream(id: Int): Flow<Utilizador?> {
        return dataBaseRepository.getUtilizadorStream(id)
    }

}

data class UtilizadoresUiState(val usersList: List<Utilizador?> = listOf())

