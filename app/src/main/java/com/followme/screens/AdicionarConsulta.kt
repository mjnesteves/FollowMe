package com.followme.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.followme.AppViewModelProvider
import com.followme.R
import com.followme.data.historicomedico.Especialidade
import com.followme.data.historicomedico.Hospital
import com.followme.data.historicomedico.MenuEspecialidade
import com.followme.data.historicomedico.MenuHospital
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.data.historicomedico.validarConsulta
import com.followme.data.medicacao.Data
import kotlinx.coroutines.launch

import java.util.Date

@Composable
fun AdicionarConsulta(navController: NavController) {

    val tag = ConsultaViewModel::class.simpleName

    val consultaViewModel: ConsultaViewModel = viewModel(factory = AppViewModelProvider.Factory)

    var hospital by rememberSaveable { mutableStateOf(Hospital.HospitalAmatoLusitano.name) }

    var especialidade by rememberSaveable { mutableStateOf(Especialidade.Anestesiologia.name) }

    var dataConsulta by rememberSaveable { mutableLongStateOf(Date().time) }

    val coroutineScope = rememberCoroutineScope()

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

        Spacer(modifier = Modifier.padding(8.dp))

        MenuHospital(
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


        MenuEspecialidade(
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

        Data(
            contexto = stringResource(id = R.string.dataConsulta),
            endDate = { dataConsulta = it },
            updateViewModel = {
                consultaViewModel.onEvent(
                    ConsultaViewModel.ConsultaUIEvent.DataConsultaChanged(
                        it
                    )
                )
            },
        )


        Spacer(modifier = Modifier.padding(100.dp))


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
                    navController.navigate("HistoricoMedico")
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
                    validarConsulta(
                        especialidade = especialidade,
                        hospital = hospital,
                        dataConsulta = dataConsulta,
                        onInvalidate = {

                        },
                        onValidate = {
                            Log.d(tag, " onValidate $especialidade, $hospital, $dataConsulta")
                            coroutineScope.launch {
                                consultaViewModel.saveItem()
                                navController.popBackStack()
                            }
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
fun AdicionarConsultaPreview() {
    val navController = rememberNavController()
    AdicionarConsulta(navController = navController)
}
