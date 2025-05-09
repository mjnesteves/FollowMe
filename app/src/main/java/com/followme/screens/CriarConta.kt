package com.followme.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.R
import com.followme.componentes.BotaoLogin

import com.followme.componentes.CheckBoxTermosCondicoes

import com.followme.componentes.IntroduzirPassoword
import com.followme.componentes.IntroduzirTexto
import com.followme.componentes.Linha_Divisora
import com.followme.componentes.Login_criarConta_hiperligacao

import com.followme.componentes.TextoCentrado
import com.followme.componentes.TextoCentradoBold
//import com.followme.data.criarconta.CriarContaUIEvent
import com.followme.data.criarconta.CriarContaViewModel





@Composable
fun CriarConta(navController: NavController, criarContaViewModel: CriarContaViewModel = viewModel()){

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){


    Surface(
        color= Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)


    ){
        Column (modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            TextoCentrado(value = stringResource(id = R.string.saudacao))
            TextoCentradoBold(value = stringResource(id = R.string.criar_conta))
            Spacer(modifier = Modifier.height(20.dp))
            IntroduzirTexto(
                labelValue= stringResource(id=R.string.nome),
                painterResource = painterResource(id=R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.nome,
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaViewModel.CriarContaUIEvent.NomeChanged(it))
                },

                errorStatus = criarContaViewModel.criarContaUIState.value.erroNome
            )

            IntroduzirTexto(
                labelValue = stringResource(id=R.string.apelido),
                painterResource = painterResource(id= R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.apelido,
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.ApelidoChanged(
                            it
                        )
                    )
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroApelido
            )
            IntroduzirTexto(
                labelValue= stringResource(id=R.string.email),
                painterResource = painterResource(id= R.drawable.email),
                textValue = criarContaViewModel.criarContaUIState.value.email,
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.EmailChanged(
                            it
                        )
                    )
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroEmail
            )

            IntroduzirPassoword(
                labelValue= stringResource(id=R.string.password),
                fieldValue = criarContaViewModel.criarContaUIState.value.password,
                painterResource= painterResource(id= R.drawable.password),
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.PasswordChanged(
                            it
                        )
                    )
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroPassword
            )

            CheckBoxTermosCondicoes(
                privacidade = { navController.navigate("PoliticaPrivacidade") },
                termos = { navController.navigate("TermosCondicoes") },
                onCheckedChange = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.TermosCondicoesCheckBox(
                            it
                        )
                    )

            }
            )

            Spacer(modifier = Modifier.height(80.dp))

            BotaoLogin(
                value = stringResource(id = R.string.registo),
                onButtonClicked = {
                    criarContaViewModel.onEvent(CriarContaViewModel.CriarContaUIEvent.RegisterButtonClicked)
                    navController.navigate("Login")
                },
                isEnabled = criarContaViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(20.dp))

            Linha_Divisora()

            Login_criarConta_hiperligacao(
                login = {navController.navigate("Login")},
                criarConta = {navController.navigate("CriarConta")},
                tryingToLogin = true)


        }
    }

    if(criarContaViewModel.signUpInProgress.value){
        CircularProgressIndicator()
    }

        //Botão de retroceder carregado
        BackHandler {
            navController.popBackStack()
        }
    }
}



