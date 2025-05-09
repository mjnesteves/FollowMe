package com.followme.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.AppViewModelProvider
import com.followme.componentes.AppToolbar
import com.followme.componentes.BotaoAdicionar
import com.followme.componentes.CenteredBottomAppBar
import com.followme.componentes.NavigationDrawerBody
import com.followme.componentes.NavigationDrawerHeader
import com.followme.componentes.Texto
import com.followme.data.home.HomeViewModel
import com.followme.data.medicacao.MedicacaoViewModel
import kotlinx.coroutines.launch

@Composable
fun Medicacao(
    navController: NavController,
    viewModel: MedicacaoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val medicacaoUiState by viewModel.medicacaoUiState.collectAsState()

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
            BotaoAdicionar(onClick = { navController.navigate("AdicionarMedicamento") })
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {


            Texto(value = "Medicacao")


        }
    }


    BackHandler {
        navController.popBackStack()
    }


}


@Preview
@Composable
fun MedicacaoPreview() {
    val navController = rememberNavController()
    Medicacao(navController)
}