package com.followme.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.followme.R
import com.followme.components.HeadingTextComponent



@Composable
fun PoliticaPrivacidade(navController: NavController, ){

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
        ) {
        HeadingTextComponent(value= stringResource(id= R.string.politica_privacidade_cabecalho))
    }


        BackHandler {
            navController.popBackStack()
        }

        /*
    SystemBackButtonHandler{
        PostOfficeAppRouter.navigateTo(Screen.CriarConta)
    }

     */
        }




}

