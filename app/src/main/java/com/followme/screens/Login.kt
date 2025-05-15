package com.followme.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.R

import com.followme.componentes.Alerta_Erro_Login


import com.followme.componentes.BotaoLogin
import com.followme.componentes.Hiperligacao_Sublinhado
import com.followme.componentes.IntroduzirPassoword
import com.followme.componentes.IntroduzirTextoEmail
import com.followme.componentes.Linha_Divisora
import com.followme.componentes.Login_criarConta_hiperligacao
import com.followme.componentes.TextoCentrado
import com.followme.componentes.TextoCentradoBold
import com.followme.data.login.LoginUIEvent
import com.followme.data.login.LoginViewModel



@Composable

fun Login(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    val loginInProgress by loginViewModel.loginInProgress


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)


        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.logotipo),
                    contentDescription = "logotipo",
                    modifier = Modifier
                        .size(400.dp, 150.dp)
                        .align(alignment = Alignment.CenterHorizontally)

                )
                Spacer(modifier = Modifier.height(25.dp))

                TextoCentrado(value = stringResource(id = R.string.login))

                TextoCentradoBold(value = stringResource(id = R.string.bem_vindo))

                Spacer(modifier = Modifier.height(20.dp))

                IntroduzirTextoEmail(
                    labelValue = stringResource(id = R.string.email),
                    textValue = loginViewModel.loginUIState.value.email,
                    painterResource(id = R.drawable.email),

                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroEmail
                )

                IntroduzirPassoword(
                    labelValue = stringResource(id = R.string.password),
                    fieldValue = loginViewModel.loginUIState.value.password,
                    painterResource(id = R.drawable.password),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroPassword
                )

                Spacer(modifier = Modifier.height(40.dp))

                //Hiperligacao_Sublinhado(value = stringResource(id = R.string.recuperar_password))

                BotaoLogin(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                Linha_Divisora()
                Login_criarConta_hiperligacao(
                    login = { navController.navigate("Login") },
                    criarConta = { navController.navigate("CriarConta") },
                    tryingToLogin = false
                )


            }
        }

        if (loginInProgress) {
            CircularProgressIndicator()
        }

        if (loginViewModel.getLoginError()) {
            Alerta_Erro_Login()
        } else if (loginViewModel.getLoginStatus()) {
            navController.navigate("Utilizadores")
        }


        BackHandler {
            navController.popBackStack()
        }


        /*
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.CriarConta)
        }

         */
    }
}

