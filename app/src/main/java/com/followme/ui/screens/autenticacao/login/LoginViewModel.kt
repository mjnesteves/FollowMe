package com.followme.ui.screens.autenticacao.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.followme.ui.screens.autenticacao.validar.Validator
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel:ViewModel() {


    data class LoginUIState(

        var email: String = "",
        var password: String = "",

        var erroEmail: Boolean = false,
        var erroPassword: Boolean = false


    )


    sealed class LoginUIEvent {


        data class EmailChanged(val email: String) : LoginUIEvent()
        data class PasswordChanged(val password: String) : LoginUIEvent()

        object LoginButtonClicked : LoginUIEvent()


    }


    private val TAG= LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    private var loginStatus = mutableStateOf(false)
    private var loginError = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )

            }

            is LoginUIEvent.PasswordChanged ->{
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )

            }

            is LoginUIEvent.LoginButtonClicked ->{
                login()

            }


        }
        validarUIDataRegras()
        printState()
    }

    private fun printState(){
        Log.d(TAG, loginUIState.value.toString())
    }


    private fun validarUIDataRegras(){

        val resValidaEmail = Validator.validaEmail(
            email = loginUIState.value.email
        )

        val resValidaPassword = Validator.validaPassword(
            password = loginUIState.value.password
        )


        allValidationsPassed.value = resValidaEmail.status && resValidaPassword.status

        Log.d(TAG, "Validar_Regras")
        Log.d(TAG, "email=$resValidaEmail")
        Log.d(TAG, "password=$resValidaPassword")

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


    private fun login(){

        loginInProgress.value = true

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{

                Log.d(TAG, "Inside_login_sucesseful")
                Log.d(TAG, "${it.isSuccessful}")

                if(it.isSuccessful){
                    loginInProgress.value = false
                    loginStatus.value = true
                }

            }
            .addOnFailureListener{
                Log.d(TAG, "Inside_login_failure")
                Log.d(TAG, "${it.localizedMessage}")

                loginInProgress.value=false
                loginError.value = true
            }
        loginError.value = false
        loginStatus.value = false
    }
}