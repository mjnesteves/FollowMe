package com.followme.ui.screens.historico_medico

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.R
import com.followme.ui.screens.home.componentes.AppToolbar
import com.followme.ui.screens.home.componentes.BotaoAdicionar
import com.followme.ui.screens.home.componentes.CenteredBottomAppBar
import com.followme.ui.screens.home.componentes.NavigationDrawerBody
import com.followme.ui.screens.home.componentes.NavigationDrawerHeader

import com.followme.data.entidades.Consulta
import kotlinx.coroutines.launch
import com.followme.di.AppViewModelProvider
import com.followme.ui.screens.historico_medico.consulta.ConsultaViewModel
import com.followme.ui.screens.home.HomeViewModel


@Composable
fun HistoricoMedico(
    navController: NavController,
) {

    val consultaViewModel: ConsultaViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val historicoMedicoViewModel: HistoricoMedicoViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val historicoMedicoUiState by historicoMedicoViewModel.historicoMedicoUiState.collectAsState()
    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val utilizadorUIStateFlow by homeViewModel.utilizadorUIState.collectAsState()
    val nomeUtilizador = utilizadorUIStateFlow.nomeUtilizador

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var confirmarLogout by remember { mutableStateOf(false) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                userName = homeViewModel.getDisplayName(),
                logoutButtonClicked = {
                    confirmarLogout = true
                },
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(homeViewModel.getDisplayName())
            NavigationDrawerBody(
                navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    navController.navigate(it.navegar)
                    Log.d("ComingHere", "inside_NavigationItemClicked")
                    Log.d("ComingHere", "${it.navegar} ${it.titulo}")
                })
        },

        bottomBar = {
            CenteredBottomAppBar(navegar = {
                navController.navigate("Home?idUtilizador=${utilizadorUIStateFlow.idUtilizador}")
            })
        },

        floatingActionButton = {
            BotaoAdicionar(
                navegar = {
                    navController.navigate("AdicionarConsulta?idUtilizador=${utilizadorUIStateFlow.idUtilizador}")
                })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {

            HistoricoMedicoBody(
                navController = navController,
                consultaViewModel = consultaViewModel,
                itemList = historicoMedicoUiState.consultaList,
                contentPadding = innerPadding,
                nomeUtilizador = nomeUtilizador,
            )
        }
        if (confirmarLogout) {
            AlertDialog(
                onDismissRequest = { confirmarLogout = false },
                confirmButton = {
                    TextButton(onClick = {
                        confirmarLogout = false

                        coroutineScope.launch {
                            homeViewModel.terminarSessao()
                        }
                        navController.navigate("Login")

                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { confirmarLogout = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Logout") },
                text = { Text("Deseja Terminar Sessão?") }
            )
        }
    }

    BackHandler {
        navController.popBackStack()
    }
}


@Composable
private fun HistoricoMedicoBody(
    consultaViewModel: ConsultaViewModel,
    navController: NavController,
    itemList: List<Consulta?>,
    contentPadding: PaddingValues,
    nomeUtilizador: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (itemList.isEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Perfil de $nomeUtilizador",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.HistoricoMedico),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                )
        } else {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = nomeUtilizador,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.HistoricoMedico),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                )

            InventoryList(
                consultaViewModel = consultaViewModel,
                itemList = itemList,
                contentPadding = contentPadding,
                navController = navController,
                )
        }
    }
}


@Composable
private fun InventoryList(
    navController: NavController,
    consultaViewModel: ConsultaViewModel,
    itemList: List<Consulta?>,
    contentPadding: PaddingValues,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(items = itemList, key = { it?.idConsulta ?: 0 }) { item ->
            if (item != null) {
                InventoryItem(
                    navController = navController,
                    consultaViewModel = consultaViewModel,
                    item = item,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                )
            }
        }
    }
}


@Composable
private fun InventoryItem(
    consultaViewModel: ConsultaViewModel,
    item: Consulta, modifier: Modifier = Modifier,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    var showConfirmDelete by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    OutlinedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7F)
                    .padding(top = 20.dp, start = 20.dp, end = 5.dp, bottom = 10.dp)
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    text = "Consulta " + item.idConsulta,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = item.especialidade,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("EditarConsulta/${item.idUtilizador}?idConsulta=${item.idConsulta}")
                    }
                ) {
                    Icon(
                        imageVector = Filled.Edit,
                        contentDescription = "",
                        )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            ) {
                if (showConfirmDelete) {
                    AlertDialog(
                        onDismissRequest = { showConfirmDelete = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showConfirmDelete = false
                                coroutineScope.launch {
                                    consultaViewModel.apagarConsulta(item)
                                }
                            }) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showConfirmDelete = false }) {
                                Text("Cancelar")
                            }
                        },
                        title = { Text("Apagar Consulta?") },
                        text = { Text("Apagar a consulta ${item.idConsulta}?") }
                    )
                }
                IconButton(
                    onClick = {
                        showConfirmDelete = true
                    }
                ) {
                    Icon(imageVector = Filled.Delete, contentDescription = "")
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp, end = 20.dp, bottom = 5.dp)
            ) {
                IconButton(
                    onClick =
                        { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        }
                    )
                }
            }
        }
        Row(
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            ) {
                if (expanded) {
                    Text(
                        text = "Hora - " + item.horaConsulta,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Data - " + item.dataConsulta,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Hospital - " + item.hospital,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}






@Preview
@Composable
fun HistoricoMedicoPreview1() {
    val navController = rememberNavController()
    HistoricoMedico(navController)
}
