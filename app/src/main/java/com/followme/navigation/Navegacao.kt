package com.followme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.followme.screens.CriarConta
import com.followme.screens.Home
import com.followme.screens.Login
import com.followme.screens.PoliticaPrivacidade
import com.followme.screens.TermosCondicoes
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.followme.data.dataBase.Utilizador
import com.followme.data.historicomedico.consulta.ConsultaViewModel
import com.followme.screens.AdicionarConsulta
import com.followme.screens.AdicionarMedicamento
import com.followme.screens.AdicionarUtilizador
import com.followme.screens.Definicoes
import com.followme.screens.EditarConsulta
import com.followme.screens.EditarMedicamento
import com.followme.screens.EditarUtilizador
import com.followme.screens.HistoricoMedico
import com.followme.screens.Info
import com.followme.screens.SinaisVitais
import com.followme.screens.Medicacao
import com.followme.screens.Utilizadores


@Composable

fun Navegacao(){

    //NavController
    val navController = rememberNavController()

    //NavHost
    NavHost(navController = navController, startDestination = "Login") {
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

    composable(
        route = "Home?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        Home(navController = navController)
    }

    composable("TermosCondicoes"){
        TermosCondicoes(navController)
    }

    composable("PoliticaPrivacidade"){
        PoliticaPrivacidade(navController)
    }


    composable("Definicoes") {
        Definicoes(navController)
    }


    composable("Info") {
        Info(navController)
    }

    composable("SinaisVitais") {
        SinaisVitais(navController)
    }

    composable(
        route = "HistoricoMedico?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        HistoricoMedico(navController = navController)
    }



    composable(
        route = "Medicacao?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        Medicacao(navController = navController)
    }







    composable(
        route = "AdicionarMedicamento?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        AdicionarMedicamento(navController = navController)
    }



    composable(
        route = "AdicionarConsulta?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        AdicionarConsulta(navController = navController)
    }


    composable(
        "EditarConsulta/{idUtilizador}?idConsulta={idConsulta}",
        arguments = listOf(
            navArgument("idUtilizador") { type = NavType.IntType },
            navArgument("idConsulta") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        EditarConsulta(navController)
    }

    composable(
        "EditarMedicamento/{idUtilizador}?idMedicamento={idMedicamento}",
        arguments = listOf(
            navArgument("idUtilizador") { type = NavType.IntType },
            navArgument("idMedicamento") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        EditarMedicamento(navController)
    }

    composable("Utilizadores") {
        Utilizadores(navController)
    }

    composable("AdicionarUtilizador") {
        AdicionarUtilizador(navController)
    }

    composable(
        route = "EditarUtilizador?idUtilizador={idUtilizador}",
        arguments = listOf(
            navArgument("idUtilizador") {
                type = NavType.IntType
                defaultValue = -1 // This makes it "optional"
            }
        )
    ) {
        EditarUtilizador(navController = navController)
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
