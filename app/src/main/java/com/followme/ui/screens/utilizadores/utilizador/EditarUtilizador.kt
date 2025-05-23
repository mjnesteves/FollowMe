package com.followme.ui.screens.utilizadores.utilizador

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.di.AppViewModelProvider
import com.followme.R
import kotlinx.coroutines.launch

@Composable
fun EditarUtilizador(navController: NavController) {

    val utilizadorViewModel: UtilizadorViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val utilizadorUiState by utilizadorViewModel.utilizadorUIStateFlow.collectAsState()

    var nomeUtilizador by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = spacedBy(8.dp),

        ) {

        Spacer(modifier = Modifier.padding(25.dp))

        Text(
            text = stringResource(id = R.string.adicionarUtilizador),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.nomeUtilizador),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = utilizadorUiState.nomeUtilizador,
            onValueChange = {
                nomeUtilizador = it
                utilizadorViewModel.onEvent(
                    UtilizadorViewModel.UtilizadorUIEvent.NomeUtilizadorChanged(
                        it
                    )
                )
            },
            placeholder = {
                Text(text = "Nome a apresentar")
            },
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = spacedBy(16.dp)

        ) {
            Button(
                modifier = Modifier

                    .height(56.dp)
                    .width(150.dp),
                onClick = {
                    navController.navigate("Utilizadores")
                }
            ) {
                Text(
                    text = stringResource(id = R.string.cancelar),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            spacedBy(50.dp)

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .width(150.dp),
                onClick = {
                    coroutineScope.launch {
                        utilizadorViewModel.atualizarUtilizador()
                    }
                    navController.popBackStack()
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.guardar),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}