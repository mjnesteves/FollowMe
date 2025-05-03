package com.followme.screens

import android.util.Log

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding


import androidx.compose.material.Scaffold

import androidx.compose.material.rememberScaffoldState


import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.rememberNavController
import com.followme.R
import com.followme.components.ButtonComponent
import com.followme.data.home.AppToolbar

import com.followme.data.home.HomeViewModel
import com.followme.data.home.NavigationDrawerBody
import com.followme.data.home.NavigationDrawerHeader

import kotlinx.coroutines.launch

@Composable
fun Home(homeViewModel: HomeViewModel = viewModel()) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(userName =  homeViewModel.getDisplayName(),
                logoutButtonClicked = {
                    //homeViewModel.terminarSessao()
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
        }









    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp),

                verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {



            Spacer(modifier = Modifier.height(200.dp))

            ButtonComponent(
                value = stringResource(id = R.string.SinaisVitais),
                onButtonClicked = {


                }
                //isEnabled = loginViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(100.dp))

            ButtonComponent(
                value = stringResource(id = R.string.HistoricoMedico),
                onButtonClicked = {


                }
                //isEnabled = loginViewModel.allValidationsPassed.value
            )



        }
    }
}

@Preview
@Composable
fun HomePreview(){
    Home()
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