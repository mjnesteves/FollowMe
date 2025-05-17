package com.followme.ui.navegacao

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.followme.ui.screens.autenticacao.criarconta.CriarConta
import com.followme.ui.screens.autenticacao.login.Login
import com.followme.ui.screens.historico_medico.HistoricoMedico
import com.followme.ui.screens.historico_medico.consulta.AdicionarConsulta
import com.followme.ui.screens.historico_medico.consulta.EditarConsulta
import com.followme.ui.screens.home.Home
import com.followme.ui.screens.medicacao.Medicacao
import com.followme.ui.screens.medicacao.medicamento.AdicionarMedicamento
import com.followme.ui.screens.medicacao.medicamento.EditarMedicamento
import com.followme.ui.screens.politica_privacidade.PoliticaPrivacidade
import com.followme.ui.screens.politica_privacidade.TermosCondicoes
import com.followme.ui.screens.sinais_vitais.SinaisVitais
import com.followme.ui.screens.utilizadores.Utilizadores
import com.followme.ui.screens.utilizadores.utilizador.AdicionarUtilizador
import com.followme.ui.screens.utilizadores.utilizador.EditarUtilizador
import com.followme.ui.screens.utils.Definicoes
import com.followme.ui.screens.utils.Info


fun NavGraphBuilder.navigationGraph(navController: NavController) {

    //Destinos
    composable("Login") {
        Login(navController)
    }

    composable("CriarConta") {
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

    composable("TermosCondicoes") {
        TermosCondicoes(navController)
    }

    composable("PoliticaPrivacidade") {
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
        "EditarConsulta?idConsulta={idConsulta}",
        arguments = listOf(
            navArgument("idConsulta") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        EditarConsulta(navController)
    }

    composable(
        "EditarMedicamento?idMedicamento={idMedicamento}",
        arguments = listOf(
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