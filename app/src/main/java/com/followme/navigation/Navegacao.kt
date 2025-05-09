package com.followme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.screens.AdicionarConsulta
import com.followme.screens.AdicionarMedicamento
import com.followme.screens.HistoricoMedico
import com.followme.screens.SinaisVitais
import com.followme.screens.Medicacao

@Composable

fun Navegacao(){

    //NavController
    val navController = rememberNavController()

    //NavHost
    NavHost(navController = navController, startDestination = "Home") {
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
        Home(navController)
    }

    composable("TermosCondicoes"){
        TermosCondicoes(navController)
    }

    composable("PoliticaPrivacidade"){
        PoliticaPrivacidade(navController)
    }

    composable("SinaisVitais") {
        SinaisVitais(navController)
    }

    composable("HistoricoMedico") {
        HistoricoMedico(navController)
    }

    composable("Medicacao") {
        Medicacao(navController)
    }

    composable("AdicionarMedicamento") {
        AdicionarMedicamento(navController)
    }

    composable("AdicionarConsulta") {
        AdicionarConsulta(navController)
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
