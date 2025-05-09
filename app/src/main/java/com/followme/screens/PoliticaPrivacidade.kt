package com.followme.screens

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
import com.followme.componentes.Botao
import com.followme.componentes.Texto
import com.followme.componentes.TextoCentradoBold


@Composable
fun PoliticaPrivacidade(navController: NavController) {

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
            TextoCentradoBold(value = stringResource(id = R.string.politica_privacidade_cabecalho))
            Spacer(modifier = Modifier.height(20.dp))
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

