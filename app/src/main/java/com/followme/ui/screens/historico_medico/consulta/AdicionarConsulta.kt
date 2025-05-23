package com.followme.ui.screens.historico_medico.consulta

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.di.AppViewModelProvider
import com.followme.R
import com.followme.ui.screens.historico_medico.consulta.entidades.Especialidade
import com.followme.ui.screens.historico_medico.consulta.entidades.Hospital
import com.followme.ui.screens.historico_medico.consulta.entidades.getEspecialidade
import com.followme.ui.screens.historico_medico.consulta.entidades.getHospital
import com.followme.ui.screens.historico_medico.consulta.validar.validarConsulta
import com.followme.ui.screens.home.HomeViewModel
import com.followme.ui.screens.medicacao.medicamento.componentes.Data
import com.followme.ui.screens.medicacao.medicamento.componentes.Hora
import kotlinx.coroutines.launch

@Composable
fun AdicionarConsulta(navController: NavController) {

    val tag = ConsultaViewModel::class.simpleName

    val consultaViewModel: ConsultaViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val consultaUiState by consultaViewModel.consultaUIStateFlow.collectAsState()

    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val utilizadorUIStateFlow by homeViewModel.utilizadorUIState.collectAsState()


    var hospital by rememberSaveable { mutableStateOf(Hospital.HospitalAmatoLusitano.name) }
    var especialidade by rememberSaveable { mutableStateOf(Especialidade.Anestesiologia.name) }
    var horaConsulta by rememberSaveable { mutableStateOf("") }
    var dataConsulta by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    var mostrarInfo by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = spacedBy(8.dp),

        ) {

        Spacer(modifier = Modifier.padding(25.dp))

        Text(
            text = stringResource(id = R.string.adicionarConsulta),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = utilizadorUIStateFlow.nomeUtilizador,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MenuHospitalAdicionar(
            hospital = { hospital = it },
            updateViewModel = {
                consultaViewModel.onEvent(
                    ConsultaViewModel.ConsultaUIEvent.HospitalChanged(
                        it
                    )
                )
            },
        )

        Spacer(modifier = Modifier.padding(8.dp))

        MenuEspecialidadeAdicionar(
            especialidade = { especialidade = it },
            updateViewModel = {
                consultaViewModel.onEvent(
                    ConsultaViewModel.ConsultaUIEvent.EspecialidadeChanged(
                        it
                    )
                )
            },
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            horizontalArrangement = spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.45f)
            ) {
                Hora(
                    contexto = stringResource(id = R.string.horaConsulta),
                    hora = { horaConsulta = it },
                    updateViewModel = {
                        consultaViewModel.onEvent(
                            ConsultaViewModel.ConsultaUIEvent.HoraConsultaChanged(
                                it
                            )
                        )
                    },
                    uiState = consultaUiState,
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.60f)
            ) {
                Data(
                    contexto = stringResource(id = R.string.dataConsulta),
                    data = { dataConsulta = it },
                    updateViewModel = {
                        consultaViewModel.onEvent(
                            ConsultaViewModel.ConsultaUIEvent.DataConsultaChanged(
                                it
                            )
                        )
                    },
                    uiState = consultaUiState,
                )
            }
        }

        Spacer(modifier = Modifier.padding(60.dp))

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
                    navController.navigate("HistoricoMedico?idUtilizador=${consultaUiState.idUtilizador}")
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
                    Log.d(
                        tag,
                        "Consulta:, $especialidade, $hospital, $horaConsulta, $dataConsulta"
                    )

                    val validarDados = (validarConsulta(
                        especialidade = especialidade,
                        hospital = hospital,
                        horaConsulta = horaConsulta,
                        dataConsulta = dataConsulta
                    ))

                    if (!validarDados) {
                        mostrarInfo = true
                    } else {
                        Log.d(tag, "Validar Dados: $especialidade, $hospital, $dataConsulta")
                        coroutineScope.launch {
                            consultaViewModel.inserirConsulta()
                        }
                        navController.navigate("HistoricoMedico?idUtilizador=${consultaUiState.idUtilizador}")
                    }
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.guardar),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        if (mostrarInfo) {
            AlertDialog(
                onDismissRequest = { mostrarInfo = false },
                confirmButton = {
                    TextButton(onClick = { mostrarInfo = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Validar Informação") },
                text = { Text("Existem campos sem valor") }
            )
        }
    }
    BackHandler {
        navController.navigate("HistoricoMedico?idUtilizador=${consultaUiState.idUtilizador}")
    }

}


@Preview(showBackground = true)
@Composable
fun AdicionarConsultaPreview() {
    val navController = rememberNavController()
    AdicionarConsulta(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuHospitalAdicionar(
    hospital: (String) -> Unit,
    updateViewModel: (String) -> Unit
) {
    Column(
        verticalArrangement = spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hospital),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getHospital().map { it.displayName }
        var expanded by rememberSaveable { mutableStateOf(false) }
        var selectedOptionText by rememberSaveable { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(PrimaryNotEditable, true),
                placeholder = { Text("Selecione o Hospital") },
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {
                    selectedOptionText = it
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            hospital(selectionOption)
                            updateViewModel(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuEspecialidadeAdicionar(
    especialidade: (String) -> Unit,
    updateViewModel: (String) -> Unit
) {
    Column(
        verticalArrangement = spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.especialidade),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getEspecialidade().map { it.displayName }
        var expanded by rememberSaveable { mutableStateOf(false) }
        var selectedOptionText by rememberSaveable { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(PrimaryNotEditable, true),
                placeholder = { Text("Selecione a Especialidade") },
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {

                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            especialidade(selectionOption)
                            updateViewModel(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

