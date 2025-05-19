package com.followme.ui.screens.autenticacao.criarconta

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
import com.followme.ui.screens.autenticacao.componentes.BotaoLogin

import com.followme.ui.screens.autenticacao.componentes.CheckBoxTermosCondicoes

import com.followme.ui.screens.autenticacao.componentes.IntroduzirPassoword
import com.followme.ui.screens.autenticacao.componentes.IntroduzirTextoEmail
import com.followme.ui.screens.autenticacao.componentes.IntroduzirTextoNormal
import com.followme.ui.screens.autenticacao.componentes.Linha_Divisora
import com.followme.ui.screens.autenticacao.componentes.Login_criarConta_hiperligacao

import com.followme.ui.screens.autenticacao.componentes.TextoCentradoBold



@Composable
fun CriarConta(navController: NavController, criarContaViewModel: CriarContaViewModel = viewModel()){

    Box(
        modifier = Modifier
            .fillMaxSize(),
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
            .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.logotipo),
                contentDescription = "logotipo",
                modifier = Modifier
                    .size(400.dp, 150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )

            TextoCentradoBold(value = stringResource(id = R.string.criar_conta))
            Spacer(modifier = Modifier.height(5.dp))
            IntroduzirTextoNormal(
                labelValue= stringResource(id=R.string.nome),
                painterResource = painterResource(id=R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.nome,
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaViewModel.CriarContaUIEvent.NomeMudou(it))
                },

                errorStatus = criarContaViewModel.criarContaUIState.value.erroNome
            )

            IntroduzirTextoNormal(
                labelValue = stringResource(id=R.string.apelido),
                painterResource = painterResource(id= R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.apelido,
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.ApelidoMudou(
                            it
                        )
                    )
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroApelido
            )
            IntroduzirTextoEmail(
                labelValue= stringResource(id=R.string.email),
                painterResource = painterResource(id= R.drawable.email),
                textValue = criarContaViewModel.criarContaUIState.value.email,
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.EmailMudou(
                            it
                        )
                    )
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroEmail
            )

            IntroduzirPassoword(
                labelValue= stringResource(id=R.string.password),
                fieldValue = criarContaViewModel.criarContaUIState.value.password,
                icon = painterResource(id = R.drawable.password),
                onTextSelected = {
                    criarContaViewModel.onEvent(
                        CriarContaViewModel.CriarContaUIEvent.PasswordMudou(
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

                })
            Spacer(modifier = Modifier.height(10.dp))
            BotaoLogin(
                value = stringResource(id = R.string.registo),
                onButtonClicked = {
                    criarContaViewModel.onEvent(CriarContaViewModel.CriarContaUIEvent.RegisterButtonClicked)
                    navController.navigate("Login")
                },
                isEnabled = criarContaViewModel.validacoes.value
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



