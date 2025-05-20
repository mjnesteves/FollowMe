package com.followme.ui.navegacao

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost


@Composable

fun Navegacao(){

    //NavController
    val navController = rememberNavController()

    //NavHost
    NavHost(navController = navController, startDestination = "CriarConta") {
        navigationGraph(navController=navController)
    }

}


