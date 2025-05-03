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
import com.followme.components.ButtonComponent
import com.followme.components.CheckboxComponent
import com.followme.components.ClickableLoginComponent
import com.followme.components.DividerTextComponent
import com.followme.components.HeadingTextComponent
import com.followme.components.MyTextFieldComponent
import com.followme.components.NormalTextComponent
import com.followme.components.PasswordTextFieldComponent
import com.followme.data.criarconta.CriarContaUIEvent
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
            NormalTextComponent(value= stringResource(id= R.string.saudacao))
            HeadingTextComponent(value= stringResource(id= R.string.criar_conta))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(
                labelValue= stringResource(id=R.string.nome),
                painterResource = painterResource(id=R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.nome,
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaUIEvent.NomeChanged(it))
                },

                errorStatus = criarContaViewModel.criarContaUIState.value.erroNome
            )

            MyTextFieldComponent(
                labelValue = stringResource(id=R.string.apelido),
                painterResource = painterResource(id= R.drawable.perfil),
                textValue = criarContaViewModel.criarContaUIState.value.apelido,
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaUIEvent.ApelidoChanged(it))
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroApelido
            )
            MyTextFieldComponent(
                labelValue= stringResource(id=R.string.email),
                painterResource = painterResource(id= R.drawable.email),
                textValue = criarContaViewModel.criarContaUIState.value.email,
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaUIEvent.EmailChanged(it))
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroEmail
            )

            PasswordTextFieldComponent(
                labelValue= stringResource(id=R.string.password),
                fieldValue = criarContaViewModel.criarContaUIState.value.password,
                painterResource= painterResource(id= R.drawable.password),
                onTextSelected = {
                    criarContaViewModel.onEvent(CriarContaUIEvent.PasswordChanged(it))
                },
                errorStatus = criarContaViewModel.criarContaUIState.value.erroPassword
            )

            CheckboxComponent(
                privacidade = { navController.navigate("PoliticaPrivacidade") },
                termos = { navController.navigate("TermosCondicoes") },
                onCheckedChange = {
                criarContaViewModel.onEvent(CriarContaUIEvent.TermosCondicoesCheckBox(it))

            }
            )

            Spacer(modifier = Modifier.height(80.dp))

            ButtonComponent(value= stringResource(id= R.string.registo),
                onButtonClicked = {
                    criarContaViewModel.onEvent(CriarContaUIEvent.RegisterButtonClicked)
                    navController.navigate("Login")
                },
                isEnabled = criarContaViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            ClickableLoginComponent(
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


    /*
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.CriarConta)
    }

     */
}
}



