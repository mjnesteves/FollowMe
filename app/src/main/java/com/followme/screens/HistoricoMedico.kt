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
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.followme.R
import com.followme.componentes.AppToolbar
import com.followme.componentes.BotaoAdicionar
import com.followme.componentes.CenteredBottomAppBar
import com.followme.data.home.HomeViewModel
import com.followme.componentes.NavigationDrawerBody
import com.followme.componentes.NavigationDrawerHeader
import com.followme.data.dataBase.Consulta
import com.followme.data.historicomedico.HistoricoMedicoViewModel
import kotlinx.coroutines.launch
import com.followme.AppViewModelProvider

@Composable
fun HistoricoMedico(
    navController: NavController,
    viewModel: HistoricoMedicoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {


    val historicoMedicoUiState by viewModel.historicoMedicoUiState.collectAsState()

    val homeViewModel: HomeViewModel = viewModel()

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
                    Log.d("ComingHere", "${it.itemId} ${it.title}")
                })
        },

        bottomBar = {
            CenteredBottomAppBar(navController)
        },

        floatingActionButton = {
            BotaoAdicionar(onClick = { navController.navigate("AdicionarConsulta") })
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {


            HistoricoMedicoBody(
                itemList = historicoMedicoUiState.consultaList,
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
private fun HistoricoMedicoBody(
    itemList: List<Consulta>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                itemList = itemList,
                //onItemClick = {}, //onItemClick(it.id) },
                contentPadding = contentPadding,

                )
        }
    }
}


@Composable
private fun InventoryList(
    itemList: List<Consulta>,
    contentPadding: PaddingValues,

    ) {
    LazyColumn(
        modifier = Modifier,
        contentPadding = contentPadding
    ) {

        items(items = itemList, key = { it.idConsulta }) { item ->
            InventoryItem(
                item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }


    }
}


@Composable
private fun InventoryItem(
    item: Consulta, modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                    .weight(1f)
                    .padding(20.dp)
            ) {

                Text(
                    text = "Consulta " + item.idConsulta + " - " + item.especialidade,
                    style = MaterialTheme.typography.titleSmall,
                )

                if (expanded) {
                    Text(
                        text = "Hospital - " + item.hospital,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "Data - " + item.dataConsulta,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
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
}


@Preview
@Composable
fun HistoricoMedicoPreview1() {
    val navController = rememberNavController()
    HistoricoMedico(navController)
}
