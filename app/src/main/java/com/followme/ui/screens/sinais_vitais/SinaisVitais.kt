package com.followme.ui.screens.sinais_vitais

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.di.AppViewModelProvider
import com.followme.R
import com.followme.ui.screens.home.componentes.AppToolbar
import com.followme.ui.screens.home.componentes.CenteredBottomAppBar
import com.followme.ui.screens.home.componentes.NavigationDrawerBody
import com.followme.ui.screens.home.componentes.NavigationDrawerHeader
import com.followme.ui.screens.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun SinaisVitais(navController: NavController) {

    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val utilizadorUIStateFlow by homeViewModel.utilizadorUIState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var confirmarLogout by remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                userName = homeViewModel.getDisplayName(),
                logoutButtonClicked = {
                    confirmarLogout = false
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


        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {

            Spacer(modifier = Modifier.height(250.dp))

            Text(
                text = stringResource(id = R.string.implementar),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

        }

        if (confirmarLogout) {
            AlertDialog(
                onDismissRequest = { confirmarLogout = false },
                confirmButton = {
                    TextButton(onClick = {
                        confirmarLogout = true

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