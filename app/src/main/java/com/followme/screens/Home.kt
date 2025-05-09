package com.followme.screens

import android.util.Log
import androidx.activity.compose.BackHandler

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer



import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState


import androidx.compose.runtime.Composable

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.followme.R
import com.followme.componentes.AppToolbar
import com.followme.componentes.BotaoAplicacoes
import com.followme.componentes.CenteredBottomAppBar
import com.followme.componentes.CenteredNavigationFab

import com.followme.data.home.HomeViewModel
import com.followme.componentes.NavigationDrawerBody
import com.followme.componentes.NavigationDrawerHeader


import kotlinx.coroutines.launch

@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Spacer(modifier = Modifier.height(100.dp))

    Scaffold(
        scaffoldState = scaffoldState,

        topBar = {
            AppToolbar(userName =  homeViewModel.getDisplayName(),
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
            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {
                    Log.d("ComingHere","inside_NavigationItemClicked")
                    Log.d("ComingHere","${it.itemId} ${it.title}")
                })
        },

        bottomBar = { CenteredBottomAppBar(navController) },




    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState()),

        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Spacer(modifier = Modifier.height(50.dp))

            BotaoAplicacoes(
                value = stringResource(id = R.string.HistoricoMedico),
                navegar = {
                    navController.navigate("HistoricoMedico")
                }

            )
            Spacer(modifier = Modifier.height(50.dp))

            BotaoAplicacoes(
                value = stringResource(id = R.string.Medicacao),
                navegar = {
                    navController.navigate("Medicacao")
                }

            )

            Spacer(modifier = Modifier.height(50.dp))

            BotaoAplicacoes(
                value = stringResource(id = R.string.SinaisVitais),
                navegar = {
                    navController.navigate("SinaisVitais")
                }

            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }


    BackHandler {
        navController.popBackStack()
    }


}

@Preview
@Composable
fun HomePreview(){
    val navController = rememberNavController()
    Home(navController = navController)
}

@Preview
@Composable

fun CriarContaPreview(){
    val navController = rememberNavController()
    CriarConta(navController)
}

@Preview
@Composable
fun LoginPreview(){
    val navController = rememberNavController()
    Login(navController)
}

@Preview
@Composable
fun PoliticaPrivacidadePreview(){
    val navController = rememberNavController()
    PoliticaPrivacidade(navController)
}

@Preview
@Composable
fun TermosCondicoesPreview(){
    val navController = rememberNavController()
    TermosCondicoes(navController)
}

@Preview
@Composable
fun SinaisVitaisPreview() {
    val navController = rememberNavController()
    SinaisVitais(navController)
}

@Preview
@Composable
fun HistoricoMedicoPreview() {
    val navController = rememberNavController()
    HistoricoMedico(navController)
}
