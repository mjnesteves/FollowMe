package com.followme.ui.screens.autenticacao.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.followme.ui.screens.autenticacao.validar.Validar
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel:ViewModel() {

    // Classe que contém a informação do estado do objeto

    data class LoginUIState(
        var email: String = "",
        var password: String = "",
        var erroEmail: Boolean = false,
        var erroPassword: Boolean = false


    )


    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class LoginUIEvent {
        data class EmailMudou(val email: String) : LoginUIEvent()
        data class PasswordMudou(val password: String) : LoginUIEvent()
        data object LoginButtonClicked : LoginUIEvent()
    }


    // Variável utilizada para referenciar os Logs
    private val tag = LoginViewModel::class.simpleName

    // Variável que guarda o objeto no estado atual
    var loginUIState = mutableStateOf(LoginUIState())

    // Variável que guarda o estado das validações dos valores introduzidos
    var validacoes = mutableStateOf(false)

    // Variável utilizada com o CircularProgress Indicator, sinaliza quando o login está em progresso
    var loginInProgress = mutableStateOf(false)

    // variável que indica qual o estado do login
    private var loginStatus = mutableStateOf(false)

    // variável que indica se o login foi bem sucedido
    private var loginError = mutableStateOf(false)

    // Funcções modificadoras das variáveis
    fun onEvent(event: LoginUIEvent){
        //Quando o evento acontecer,
        when(event){
            // Alteração do Email
            is LoginUIEvent.EmailMudou -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )

            }

            // Alteração da Password
            is LoginUIEvent.PasswordMudou -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )

            }
            // Quando o botão de Login é pressionado
            is LoginUIEvent.LoginButtonClicked ->{
                login()

            }
        }
        // Executa a função declarada abaixo
        validarUIDataRegras()
        printEstado()
    }


    private fun printEstado() {
        Log.d(tag, loginUIState.value.toString())
    }

    // Valida as regras definidas no ficheiro ValidarDados
    private fun validarUIDataRegras(){

        val resValidaEmail = Validar.validaEmail(
            email = loginUIState.value.email
        )

        val resValidaPassword = Validar.validaPassword(
            password = loginUIState.value.password
        )


        validacoes.value = resValidaEmail.status && resValidaPassword.status

        Log.d(tag, "Validar_Regras")
        Log.d(tag, "email=$resValidaEmail")
        Log.d(tag, "password=$resValidaPassword")

        loginUIState.value = loginUIState.value.copy(
            erroEmail = resValidaEmail.status,
            erroPassword = resValidaPassword.status,
        )


    }


    fun getLoginError(): Boolean {
        return loginError.value
    }

    fun getLoginStatus(): Boolean {
        return loginStatus.value
    }


    // Função que faz o login do utilizador na plataforma Firebase
    private fun login(){

        // Mostra o CircularProgressIndicator
        loginInProgress.value = true

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{

                Log.d(tag, "A tentar fazer Login ...")
                Log.d(tag, "Login com sucesso? ${it.isSuccessful}")

                if(it.isSuccessful){
                    Log.d(tag, "Bem vindo!")
                    loginInProgress.value = false
                    loginStatus.value = true
                }

            }
            .addOnFailureListener{
                Log.d(tag, "Login sem sucesso")
                Log.d(tag, "Erro = {${it.localizedMessage}")

                loginInProgress.value=false
                loginError.value = true
            }

        // Login validado -->
        loginError.value = false
        loginStatus.value = false
    }
}