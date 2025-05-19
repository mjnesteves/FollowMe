package com.followme.ui.screens.autenticacao.login

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

import com.followme.ui.screens.autenticacao.componentes.Alerta_Erro_Login


import com.followme.ui.screens.autenticacao.componentes.BotaoLogin
import com.followme.ui.screens.autenticacao.componentes.IntroduzirPassoword
import com.followme.ui.screens.autenticacao.componentes.IntroduzirTextoEmail
import com.followme.ui.screens.autenticacao.componentes.Linha_Divisora
import com.followme.ui.screens.autenticacao.componentes.Login_criarConta_hiperligacao
import com.followme.ui.screens.autenticacao.componentes.TextoCentrado
import com.followme.ui.screens.autenticacao.componentes.TextoCentradoBold
import kotlinx.coroutines.launch


@Composable

fun Login(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    // Variável que indica que o login está em progresso.
    // Quando o valor é verdadeiro, mostra o círculo, simular processamento

    val loginEmProgresso by loginViewModel.loginInProgress

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
                Spacer(modifier = Modifier.height(10.dp))

                TextoCentrado(value = stringResource(id = R.string.login))

                TextoCentradoBold(value = stringResource(id = R.string.bem_vindo))

                Spacer(modifier = Modifier.height(20.dp))

                IntroduzirTextoEmail(
                    labelValue = stringResource(id = R.string.email),
                    textValue = loginViewModel.loginUIState.value.email,
                    painterResource(id = R.drawable.email),

                    onTextSelected = {
                        loginViewModel.onEvent(LoginViewModel.LoginUIEvent.EmailMudou(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroEmail
                )

                IntroduzirPassoword(
                    labelValue = stringResource(id = R.string.password),
                    fieldValue = loginViewModel.loginUIState.value.password,
                    painterResource(id = R.drawable.password),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginViewModel.LoginUIEvent.PasswordMudou(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroPassword
                )

                Spacer(modifier = Modifier.height(40.dp))

                //Hiperligacao_Sublinhado(value = stringResource(id = R.string.recuperar_password))

                BotaoLogin(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginViewModel.LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.validacoes.value
                )

                Linha_Divisora()
                Login_criarConta_hiperligacao(
                    login = { navController.navigate("Login") },
                    criarConta = { navController.navigate("CriarConta") },
                    tryingToLogin = false
                )


            }
        }





        if (loginEmProgresso) {
            CircularProgressIndicator()
        }

        // Se acontecer um erro no processo de Login ( email ou passoword errados ), o sistema mostra um Alert Dialog e impede o Login


        if (loginViewModel.getLoginError()) {
            Alerta_Erro_Login()
        } else if (loginViewModel.getLoginStatus()) {

            // Se o login for bem sucedido, o navegador passa para a página de Perfis de Utilizadores

            navController.navigate("Utilizadores")
        }


        // Quando é carregado o botão retroceder, o sistema volta atrás
        BackHandler {
            navController.popBackStack()
        }

    }
}

