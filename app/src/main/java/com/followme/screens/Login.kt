package com.followme.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.followme.R
import com.followme.components.AlertDialogSample

import com.followme.components.ButtonComponent
import com.followme.components.ClickableLoginComponent
import com.followme.components.DividerTextComponent
import com.followme.components.HeadingTextComponent

import com.followme.components.MyTextFieldComponent
import com.followme.components.NormalTextComponent
import com.followme.components.PasswordTextFieldComponent
import com.followme.components.UnderLinedTextComponent
import com.followme.data.login.LoginUIEvent
import com.followme.data.login.LoginViewModel




@Composable

fun Login(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

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

                NormalTextComponent(value = stringResource(id = R.string.login))

                HeadingTextComponent(value = stringResource(id = R.string.bem_vindo))

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    textValue = loginViewModel.loginUIState.value.email,
                    painterResource(id = R.drawable.email),

                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroEmail
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    fieldValue = loginViewModel.loginUIState.value.password,
                    painterResource(id = R.drawable.password),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.erroPassword
                )

                Spacer(modifier = Modifier.height(40.dp))

                UnderLinedTextComponent(value = stringResource(id = R.string.recuperar_password))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)

                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )

                DividerTextComponent()
                ClickableLoginComponent(
                    login = {navController.navigate("Login")},
                    criarConta = {navController.navigate("CriarConta")},
                    tryingToLogin = false)


            }
        }

        if(loginViewModel.loginInProgress.value){
            CircularProgressIndicator()
        }

        if(loginViewModel.getErroLogin()){
            AlertDialogSample(confirmButtonClicked = {
                loginViewModel.setErroLogin(false)
            })
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

