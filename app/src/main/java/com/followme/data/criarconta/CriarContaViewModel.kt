package com.followme.data.criarconta


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.followme.data.regras.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class CriarContaViewModel: ViewModel(){

    private val TAG= CriarContaViewModel::class.simpleName

    var criarContaUIState = mutableStateOf(CriarContaUIState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    fun onEvent(event: CriarContaUIEvent){

        when(event){
            is CriarContaUIEvent.NomeChanged ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    nome = event.nome
                )
                printState()

            }

            is CriarContaUIEvent.ApelidoChanged ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    apelido = event.apelido
                )
                printState()

            }

            is CriarContaUIEvent.EmailChanged ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    email = event.email
                )
                printState()

            }

            is CriarContaUIEvent.PasswordChanged ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    password = event.password
                )
                printState()

            }

            is CriarContaUIEvent.RegisterButtonClicked ->{
                signUp()
            }


            is CriarContaUIEvent.TermosCondicoesCheckBox ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    politicaTermosAceite  = event.statusValue

                )
                printState()

            }
        }
        validarRegras()

    }

    fun signUp(){
        Log.d(TAG, "Inside_SignUp")
        printState()


        createUserInFirebse(
            email = criarContaUIState.value.email,
            password = criarContaUIState.value.password
        )

    }

    private fun validarRegras(){

        val resValidaNome = Validator.validaNome(
            nome = criarContaUIState.value.nome
        )

        val resValidaApelido = Validator.validaApelido(
            apelido = criarContaUIState.value.apelido
        )

        val resValidaEmail = Validator.validaEmail(
            email = criarContaUIState.value.email
        )

        val resValidaPassword = Validator.validaPassword(
            password = criarContaUIState.value.password
        )

        val resValidaPoliticaTermos = Validator.validaTermosCondicoes(
        statusValue  = criarContaUIState.value.politicaTermosAceite
        )
        Log.d(TAG, "TermosCondicoes=$resValidaPoliticaTermos")

        allValidationsPassed.value = resValidaNome.status && resValidaApelido.status && resValidaEmail.status && resValidaPassword.status && resValidaPoliticaTermos.status



        Log.d(TAG, "Validar_Regras")
        Log.d(TAG, "nome=$resValidaNome")
        Log.d(TAG, "apelido=$resValidaApelido")
        Log.d(TAG, "email=$resValidaEmail")
        Log.d(TAG, "password=$resValidaPassword")
        Log.d(TAG, "TermosCondicoes=$resValidaPoliticaTermos")
        Log.d(TAG, "ValidaçõesOk? =${allValidationsPassed.value}")



        criarContaUIState.value = criarContaUIState.value.copy(
            erroNome = resValidaNome.status,
            erroApelido = resValidaApelido.status,
            erroEmail = resValidaEmail.status,
            erroPassword = resValidaPassword.status,
            politicaTermosErro = resValidaPoliticaTermos.status
        )

    }


    private fun printState(){
        Log.d(TAG, criarContaUIState.value.toString())
    }


    private fun createUserInFirebse(email:String, password:String){
        signUpInProgress.value = true
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener(){
                Log.d(TAG, "OnCompleteListener")
                Log.d(TAG, "is Successful = ${it.isSuccessful}")

                signUpInProgress.value = false

                if(it.isSuccessful){
                    updateUserFireBase(criarContaUIState.value.nome,criarContaUIState.value.apelido )
                }

            }
            .addOnFailureListener(){
                Log.d(TAG, "OnFailureListener")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }



    }

    private fun updateUserFireBase(nome:String, apelido:String){
        val user =    FirebaseAuth.getInstance().currentUser;
        val profileUpdates = userProfileChangeRequest {
            displayName = "$nome $apelido"

        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

    }


}