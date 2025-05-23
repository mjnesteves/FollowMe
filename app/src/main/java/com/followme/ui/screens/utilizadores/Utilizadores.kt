package com.followme.ui.screens.utilizadores


import androidx.activity.compose.BackHandler


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

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
import androidx.compose.ui.text.LinkAnnotation

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.R
import com.followme.ui.screens.home.componentes.AppToolbar
import com.followme.ui.screens.home.componentes.BotaoAdicionar
import com.followme.ui.screens.home.componentes.CenteredBottomAppBar
import com.followme.ui.screens.home.componentes.NavigationDrawerBody
import com.followme.ui.screens.home.componentes.NavigationDrawerHeader
import kotlinx.coroutines.launch
import com.followme.di.AppViewModelProvider
import com.followme.data.entidades.Utilizador
import com.followme.ui.screens.home.HomeViewModel
import com.followme.ui.screens.utilizadores.utilizador.UtilizadorViewModel


@Composable
fun Utilizadores(
    navController: NavController
) {

    val utilizadoresViewModel: UtilizadoresViewModel =
        viewModel(factory = AppViewModelProvider.Factory)

    val utilizadorViewModel: UtilizadorViewModel =
        viewModel(factory = AppViewModelProvider.Factory)

    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val utilizadoresUiState by utilizadoresViewModel.utilizadoresUiState.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var confirmarLogout by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(100.dp))

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
                })
        },

        bottomBar = { CenteredBottomAppBar({}) },

        floatingActionButton = {
            BotaoAdicionar(navegar = { navController.navigate("AdicionarUtilizador") })
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {

            UserListBody(
                utilizadorViewModel = utilizadorViewModel,
                navController = navController,
                itemList = utilizadoresUiState.usersList,
                contentPadding = innerPadding,
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
private fun UserListBody(
    utilizadorViewModel: UtilizadorViewModel,
    navController: NavController,
    itemList: List<Utilizador?>,
    contentPadding: PaddingValues,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {
        if (itemList.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.utilizadores),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,

                )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.semUtilizadores),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,

                )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                text = stringResource(R.string.utilizadores),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,

                )

            UserList(
                utilizadorViewModel = utilizadorViewModel,
                itemList = itemList,
                contentPadding = contentPadding,
                navController = navController
            )
        }
    }
}


@Composable
private fun UserList(
    utilizadorViewModel: UtilizadorViewModel,
    navController: NavController,
    itemList: List<Utilizador?>,
    contentPadding: PaddingValues,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(items = itemList, key = { it?.idUtilizador ?: 0 }) { item ->
            if (item != null) {
                Utilizador(
                    utilizadorViewModel = utilizadorViewModel,
                    navController = navController,
                    item = item,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .width(250.dp)
                )
            }
        }
    }
}


@Composable
private fun Utilizador(
    utilizadorViewModel: UtilizadorViewModel,
    navController: NavController,
    item: Utilizador,
    modifier: Modifier = Modifier,

    ) {

    val coroutineScope = rememberCoroutineScope()
    val nomeUtilizador = item.nomeUtilizador
    var mostrarInfo by remember { mutableStateOf(false) }


    val annotatedString = buildAnnotatedString {
        withLink(
            link = LinkAnnotation
                .Clickable(
                    tag = nomeUtilizador,
                ) {
                    navController.navigate("Home?idUtilizador=${item.idUtilizador}")
                }

        ) {
            append(nomeUtilizador)
        }
    }

    OutlinedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8F)
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
            ) {

                Text(
                    text = annotatedString,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, start = 5.dp, end = 1.dp, bottom = 10.dp)
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("EditarUtilizador?idUtilizador=${item.idUtilizador}")
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
                    .padding(top = 10.dp, start = 1.dp, end = 10.dp, bottom = 10.dp)

            ) {
                if (mostrarInfo) {
                    AlertDialog(
                        onDismissRequest = { mostrarInfo = false },
                        confirmButton = {
                            TextButton(onClick = {
                                mostrarInfo = false
                                coroutineScope.launch {
                                    utilizadorViewModel.apagarUtilizador(item)
                                }

                            }) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { mostrarInfo = false }) {
                                Text("Cancelar")
                            }
                        },
                        title = { Text("Apagar Utilizador?") },
                        text = { Text("Apagar o utilizador ${item.idUtilizador}?") }
                    )
                }
                IconButton(
                    onClick = {
                        mostrarInfo = true
                    }
                ) {
                    Icon(imageVector = Filled.Delete, contentDescription = "")
                }
            }
        }
    }
}







