package com.followme.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.AppViewModelProvider
import com.followme.R
import com.followme.componentes.AppToolbar
import com.followme.componentes.BotaoAdicionar
import com.followme.componentes.CenteredBottomAppBar
import com.followme.componentes.NavigationDrawerBody
import com.followme.componentes.NavigationDrawerHeader
import com.followme.data.dataBase.Medicamento
import com.followme.data.home.HomeViewModel
import com.followme.data.medicacao.MedicacaoViewModel
import com.followme.data.medicacao.medicamento.MedicamentoViewModel
import kotlinx.coroutines.launch

@Composable
fun Medicacao(
    navController: NavController,
    medicacaoViewModel: MedicacaoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {


    val medicacaoUiState by medicacaoViewModel.medicacaoUiState.collectAsState()
    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val medicamentoViewModel: MedicamentoViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val utilizadorUIStateFlow by homeViewModel.utilizadorUIStateFlow.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                userName = homeViewModel.getDisplayName(),
                logoutButtonClicked = {
                    homeViewModel.terminarSessao()
                    navController.navigate("Login")
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
                    Log.d("ComingHere", "inside_NavigationItemClicked")
                    Log.d("ComingHere", "${it.navigateTo} ${it.title}")
                })
        },

        bottomBar = {
            CenteredBottomAppBar(navegar = {
                navController.navigate("Home?idUtilizador=${utilizadorUIStateFlow.idUtilizador}")
            })
        },

        floatingActionButton = {
            BotaoAdicionar(navegar = { navController.navigate("AdicionarMedicamento?idUtilizador=${utilizadorUIStateFlow.idUtilizador}") })
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {


            MedicacaoBody(
                medicamentoViewModel = medicamentoViewModel,
                navController = navController,
                medicacaoViewModel = medicacaoViewModel,
                itemList = medicacaoUiState.medicamentoList,
                //onItemValueChange = viewModel::updateUiState,
                contentPadding = innerPadding,
            )


        }
    }


    BackHandler {
        navController.popBackStack()
    }
}


@Composable
private fun MedicacaoBody(
    medicamentoViewModel: MedicamentoViewModel,
    medicacaoViewModel: MedicacaoViewModel,
    navController: NavController,
    itemList: List<Medicamento>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (itemList.isEmpty()) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.Medicacao),
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.Medicacao),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,

                )
            InventoryList(
                medicamentoViewModel = medicamentoViewModel,
                medicacaoViewModel = medicacaoViewModel,
                itemList = itemList,
                //onItemClick = {}, //onItemClick(it.id) },
                contentPadding = contentPadding,
                navController = navController

            )
        }
    }
}


@Composable
private fun InventoryList(
    medicamentoViewModel: MedicamentoViewModel,
    navController: NavController,
    medicacaoViewModel: MedicacaoViewModel,
    itemList: List<Medicamento>,
    contentPadding: PaddingValues,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(items = itemList, key = { it.idMedicamento }) { item ->
            InventoryItem(
                medicamentoViewModel = medicamentoViewModel,
                navController = navController,
                medicacaoViewModel = medicacaoViewModel,
                item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }


    }
}


@Composable
private fun InventoryItem(
    medicacaoViewModel: MedicacaoViewModel,
    medicamentoViewModel: MedicamentoViewModel,
    item: Medicamento, modifier: Modifier = Modifier,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
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
                /*
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RectangleShape
                )
                */


            ) {

                Text(
                    text = "Medicamento " + item.idMedicamento,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = item.nomeMedicamento,
                    style = MaterialTheme.typography.titleMedium,
                )

            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
                /*
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RectangleShape
                )

                 */
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("EditarMedicamento?medicamentoId=${item.idMedicamento}")
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
                /*
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RectangleShape
            )

                 */
            ) {

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            medicacaoViewModel.apagarMedicamento(item)
                        }
                    }

                ) {

                    Icon(
                        imageVector = Filled.Delete,
                        contentDescription = "",
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp, end = 20.dp, bottom = 5.dp)
                /*
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RectangleShape
                )
                 */
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
                /*
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RectangleShape
                )

                 */
            ) {
                if (expanded) {
                    Text(
                        text = "Quantidade - " + item.quantidade,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Quando Toma - " + item.quandoToma,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Quando Acaba - " + item.dataFim,
                        style = MaterialTheme.typography.titleSmall
                    )

                }
            }
        }
    }
}






@Preview
@Composable
fun MedicacaoPreview1() {
    val navController = rememberNavController()
    Medicacao(navController)
}