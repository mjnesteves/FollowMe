package com.followme.ui.screens.utils

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
import androidx.compose.runtime.rememberCoroutineScope
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
fun Definicoes(navController: NavController) {

    val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

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
                    navController.navigate(it.navegar)
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

            Spacer(modifier = Modifier.height(250.dp))

            Text(
                text = stringResource(id = R.string.implementar),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
    BackHandler {
        navController.navigate("Utilizadores")
    }


}