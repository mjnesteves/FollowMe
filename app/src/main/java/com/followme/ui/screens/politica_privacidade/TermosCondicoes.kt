package com.followme.ui.screens.politica_privacidade

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.followme.R

import com.followme.ui.screens.autenticacao.componentes.TextoCentradoBold
import com.followme.ui.screens.politica_privacidade.componentes.Botao
import com.followme.ui.screens.politica_privacidade.componentes.Texto


@Composable
fun TermosCondicoes(navController: NavController) {

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(50.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TextoCentradoBold(value = stringResource(id = R.string.termos_condicoes_cabecalho))
            Spacer(modifier = Modifier.height(40.dp))
            Texto(value = stringResource(id = R.string.ConteudoPolitica))

            Botao(
                regressarLogin = { navController.popBackStack() },

                )
        }

        BackHandler {
            navController.popBackStack()
        }

    }
}

