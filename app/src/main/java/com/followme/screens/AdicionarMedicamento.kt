package com.followme.screens


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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.AppViewModelProvider
import com.followme.R
import com.followme.data.dataBase.Medicamento
import com.followme.data.historicomedico.HistoricoMedicoViewModel
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.data.medicacao.Data
import com.followme.data.medicacao.Frequencia
import com.followme.data.medicacao.MedicacaoViewModel
import com.followme.data.medicacao.MenuFrequencias
import com.followme.data.medicacao.MenuTempoDia
import com.followme.data.medicacao.TempoDia
import com.followme.data.medicacao.validarMedicacao

import java.util.Date

@Composable
fun AdicionarMedicamento(navController: NavController) {
    val medicamentoViewModel: ConsultaViewModel = viewModel(factory = AppViewModelProvider.Factory)

    var quantidade by rememberSaveable { mutableStateOf("1") }
    var nomeMedicamento by rememberSaveable { mutableStateOf("") }
    var frequencia by rememberSaveable { mutableStateOf(Frequencia.Diario.name) }

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

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.nomeMedicamento),
            style = MaterialTheme.typography.bodyLarge
        )


        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = nomeMedicamento,
            onValueChange = {
                nomeMedicamento = it
            },
            placeholder = {
                Text(text = "ex: Benuron")
            },
        )

        Spacer(modifier = Modifier.padding(4.dp))

        var quantidadeMaxima by rememberSaveable { mutableStateOf(false) }
        Row(
            horizontalArrangement = spacedBy(16.dp)
        ) {
            val maxDose = 5

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
                    value = quantidade,
                    onValueChange = {
                        if (it.length < maxDose) {
                            quantidadeMaxima = false
                            quantidade = it
                        } else {
                            quantidadeMaxima = true
                        }
                    },
                    trailingIcon = {
                        if (quantidadeMaxima) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    placeholder = { Text(text = "e.g. 1") },
                    isError = quantidadeMaxima,
                    keyboardOptions =
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            MenuFrequencias { frequencia = it }
        }

        if (quantidadeMaxima) {
            Text(
                text = "You cannot have more than 99 dosage per day.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }


        var dataFim by rememberSaveable { mutableLongStateOf(Date().time) }

        Spacer(modifier = Modifier.padding(4.dp))



        Data(
            contexto = stringResource(id = R.string.data_fim),
            endDate = { dataFim = it },
            updateViewModel = {}

        )


        Spacer(modifier = Modifier.padding(4.dp))

        var quandoToma by rememberSaveable { mutableStateOf(TempoDia.PequenoAlmoco.name) }

        MenuTempoDia { quandoToma = it }

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
                    navController.navigate("Medicacao")
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
                    //.fillMaxWidth()
                    .height(56.dp)
                    .width(150.dp),
                onClick = {
                    validarMedicacao(
                        nomeMedicamento = nomeMedicamento,
                        quantidade = quantidade.toIntOrNull() ?: 0,
                        //quando = quando,
                        dataFim = dataFim,
                        onInvalidate = {
                            /*
                        Toast.makeText(
                            context,
                            context.getString(R.string.value_is_empty, context.getString(it)),
                            Toast.LENGTH_LONG
                        ).show()

                         */
                        },
                        onValidate = {
                            /*

                        // TODO: Navigate to next screen / Store medication info
                        Toast.makeText(
                            context,
                            context.getString(R.string.success),
                            Toast.LENGTH_LONG
                        ).show()

                         */
                        }
                    )
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

    BackHandler {
        navController.popBackStack()
    }


}


@Preview(showBackground = true)
@Composable
fun AdicionarMedicamentoPreview() {
    val navController = rememberNavController()
    AdicionarMedicamento(navController = navController)
}
