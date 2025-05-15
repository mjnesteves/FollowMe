package com.followme.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.AppViewModelProvider
import com.followme.R
import com.followme.componentes.AppToolbar
import com.followme.componentes.BotaoAplicacoes
import com.followme.componentes.CenteredBottomAppBar
import com.followme.componentes.NavigationDrawerBody
import com.followme.componentes.NavigationDrawerHeader
import com.followme.data.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun Info(navController: NavController) {

    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val utilizadorUIStateFlow by homeViewModel.utilizadorUIStateFlow.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Spacer(modifier = Modifier.height(100.dp))

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
                    navController.navigate(it.navigateTo)
                })
        },

        bottomBar = {
            CenteredBottomAppBar(navegar = {
                navController.navigate("Utilizadores")
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

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = stringResource(id = R.string.info),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }


    BackHandler {
        navController.popBackStack()
    }


}