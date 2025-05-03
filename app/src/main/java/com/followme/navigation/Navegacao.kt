package com.followme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.followme.screens.CriarConta
import com.followme.screens.Home
import com.followme.screens.Login
import com.followme.screens.PoliticaPrivacidade
import com.followme.screens.TermosCondicoes
import androidx.navigation.compose.NavHost

@Composable

fun Navegacao(){

    //NavController
    val navController = rememberNavController()

    //NavHost
    NavHost(navController=navController, startDestination="Login"){
        navigationGraph(navController=navController)
    }

}


//NavGraph
fun NavGraphBuilder.navigationGraph(navController: NavController){

    //Destinations
    composable("Login"){
        Login(navController)
    }

    composable("CriarConta"){
        CriarConta(navController)
    }

    composable("Home"){
        Home()
    }

    composable("TermosCondicoes"){
        TermosCondicoes(navController)
    }

    composable("PoliticaPrivacidade"){
        PoliticaPrivacidade(navController)
    }

}






//Navigation Graph
/*
sealed class Screen(){


    object CriarConta : Screen()
    object TermosCondicoes : Screen()
    object PoliticaPrivacidade : Screen()
    object Login : Screen()
    object Home : Screen()

}





object PostOfficeAppRouter{

    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.Home)

    fun navigateTo(destination:Screen){
        currentScreen.value = destination
    }
}


*/
