package com.followme.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.followme.components.NormalTextComponent
import com.followme.data.politica.Texto


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
        ) {
        HeadingTextComponent(value= stringResource(id= R.string.politica_privacidade_cabecalho))
            Spacer(modifier = Modifier.height(40.dp))
            Texto(value = stringResource(id = R.string.ConteudoPolitica))
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

