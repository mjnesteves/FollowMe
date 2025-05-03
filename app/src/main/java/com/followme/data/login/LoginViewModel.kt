package com.followme.data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.followme.data.regras.Validator
import com.followme.navigation.Navegacao
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel:ViewModel() {

    private val TAG= LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)



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

    fun getErroLogin(): Boolean {
        return loginUIState.value.loginError
    }

    fun setErroLogin(valor:Boolean){
        loginUIState.value.loginError = valor
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

                }

            }
            .addOnFailureListener{
                Log.d(TAG, "Inside_login_failure")
                Log.d(TAG, "${it.localizedMessage}")

                setErroLogin(true)

                loginInProgress.value=false


            }
    }


}