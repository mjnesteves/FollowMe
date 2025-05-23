package com.followme.ui.screens.medicacao.medicamento


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.di.AppViewModelProvider
import com.followme.R
import com.followme.ui.screens.historico_medico.consulta.componentes.Data
import com.followme.ui.screens.home.HomeViewModel
import com.followme.ui.screens.medicacao.medicamento.entidades.Frequencia
import com.followme.ui.screens.medicacao.medicamento.entidades.TempoDia
import com.followme.ui.screens.medicacao.medicamento.entidades.getFrequencia
import com.followme.ui.screens.medicacao.medicamento.entidades.getTempoDia
import com.followme.ui.screens.medicacao.medicamento.validar.validarMedicacao
import kotlinx.coroutines.launch



@Composable
fun AdicionarMedicamento(navController: NavController) {

    val tag = MedicamentoViewModel::class.simpleName

    val medicamentoViewModel: MedicamentoViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val medicamentoUiState by medicamentoViewModel.medicamentoUIStateFlow.collectAsState()


    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val utilizadorUIStateFlow by homeViewModel.utilizadorUIState.collectAsState()

    var nomeMedicamento by rememberSaveable { mutableStateOf("") }
    var quantidade by rememberSaveable { mutableStateOf("") }
    var frequencia by rememberSaveable { mutableStateOf(Frequencia.Diario.name) }
    var dataFim by rememberSaveable { mutableStateOf("") }
    var quandoToma by rememberSaveable { mutableStateOf(TempoDia.PequenoAlmoco.name) }

    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = spacedBy(8.dp),

        ) {

        Spacer(modifier = Modifier.padding(25.dp))

        Text(

            text = stringResource(id = R.string.adicionarMedicamento),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = utilizadorUIStateFlow.nomeUtilizador,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall

        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = stringResource(id = R.string.nomeMedicamento),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = medicamentoUiState.nomeMedicamento,
            onValueChange = {
                nomeMedicamento = it
                medicamentoViewModel.onEvent(
                    MedicamentoViewModel.MedicamentoUIEvent.NomeMedicamentoMudou(
                        it
                    )
                )
            },
            placeholder = {
                Text(text = "ex: Benuron")
            },
            singleLine = true,
            maxLines = 1,

        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            horizontalArrangement = spacedBy(16.dp)
        ) {

            Column(
                verticalArrangement = spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.quantidade),
                    style = MaterialTheme.typography.bodyLarge
                )
                TextField(
                    modifier = Modifier
                        .width(128.dp),
                    placeholder = { Text(text = "ex: 1") },
                    value = quantidade,
                    onValueChange = {
                        quantidade = it
                        medicamentoViewModel.onEvent(
                            MedicamentoViewModel.MedicamentoUIEvent.QuantidadeMudou(it)
                        )
                    },

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    maxLines = 1,
                )
            }
            MenuFrequenciasAdicionar(
                frequencia = { frequencia = it },
                updateViewModel = {
                    medicamentoViewModel.onEvent(
                        MedicamentoViewModel.MedicamentoUIEvent.FrequenciaMudou(
                            it
                        )
                    )
                },
                )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Data(
            contexto = stringResource(id = R.string.data_fim),
            data = { dataFim = it },
            updateViewModel = {
                medicamentoViewModel.onEvent(
                    MedicamentoViewModel.MedicamentoUIEvent.DataFimMoudou(
                        it
                    )
                )
            },
            uiState = medicamentoUiState,
        )

        Spacer(modifier = Modifier.padding(4.dp))

        MenuTempoDiaAdicionar(
            quando = { quandoToma = it },
            updateViewModel = {
                medicamentoViewModel.onEvent(
                    MedicamentoViewModel.MedicamentoUIEvent.QuandoTomaMudou(
                        it
                    )
                )

            },
        )

        Spacer(modifier = Modifier.padding(8.dp))

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
                    navController.navigate("Medicacao?idUtilizador=${medicamentoUiState.idUtilizador}")
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

                    val result = (validarMedicacao(
                        nomeMedicamento = medicamentoUiState.nomeMedicamento,
                        quantidade = medicamentoUiState.quantidade.toString(),
                        frequencia = medicamentoUiState.frequencia,
                        dataFim = medicamentoUiState.dataFim,
                        quandoToma = medicamentoUiState.quandoToma
                    ))

                    Log.d(
                        tag,
                        "Medicamento: {$nomeMedicamento, $quantidade, $frequencia, $dataFim, $quandoToma }"
                    )

                    if (!result) {
                        showDialog = true
                    } else {
                        Log.d(
                            tag,
                            "Medicamento $nomeMedicamento, $quantidade, $frequencia, $dataFim, $quandoToma"
                        )
                        coroutineScope.launch {
                            medicamentoViewModel.inserirtMedicamento()
                        }
                        navController.navigate("Medicacao?idUtilizador=${medicamentoUiState.idUtilizador}")
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
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Validar Informação") },
                text = { Text("Existem campos sem valor") }
            )
        }
    }

    BackHandler {
        navController.navigate("Medicacao?idUtilizador=${medicamentoUiState.idUtilizador}")
    }
}


@Preview(showBackground = true)
@Composable
fun AdicionarMedicamentoPreview() {
    val navController = rememberNavController()
    AdicionarMedicamento(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuFrequenciasAdicionar(
    frequencia: (String) -> Unit,
    updateViewModel: (String) -> Unit,

    ) {
    Column(
        verticalArrangement = spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.frequencia),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getFrequencia().map { it.displayName }
        var expanded by rememberSaveable { mutableStateOf(false) }
        var selectedOptionText by rememberSaveable { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor(PrimaryNotEditable, true),
                readOnly = true,
                placeholder = { Text("Frequência") },
                value = selectedOptionText,
                onValueChange = {},
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
                            frequencia(selectionOption)
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
fun MenuTempoDiaAdicionar(
    quando: (String) -> Unit,
    updateViewModel: (String) -> Unit,

    ) {
    Column(
        verticalArrangement = spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.TempoDia),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getTempoDia().map { it.displayName }
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
                readOnly = true,
                placeholder = { Text("Quando toma") },
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
                            quando(selectionOption)
                            updateViewModel(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}