package com.followme.ui.screens.autenticacao.criarconta


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.followme.ui.screens.autenticacao.validar.Validar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class CriarContaViewModel: ViewModel(){

    // Classe que contém a informação do estado do objeto
    data class CriarContaUIState(
        var nome: String = "",
        var apelido: String = "",
        var email: String = "",
        var password: String = "",
        var politicaTermosAceite: Boolean = false,

        var erroNome: Boolean = false,
        var erroApelido: Boolean = false,
        var erroEmail: Boolean = false,
        var erroPassword: Boolean = false,
        var politicaTermosErro: Boolean = false

    )

    // Classe que contém a declaração dos métodos de modificação das variáveis
    sealed class CriarContaUIEvent {
        data class NomeMudou(val nome: String) : CriarContaUIEvent()
        data class ApelidoMudou(val apelido: String) : CriarContaUIEvent()
        data class EmailMudou(val email: String) : CriarContaUIEvent()
        data class PasswordMudou(val password: String) : CriarContaUIEvent()
        data class TermosCondicoesCheckBox(val statusValue: Boolean) : CriarContaUIEvent()
        data object RegisterButtonClicked : CriarContaUIEvent()


    }

    // Variável utilizada para referenciar os Logs
    private val tag = CriarContaViewModel::class.simpleName

    // Variável que guarda o objeto no estado atual
    var criarContaUIState = mutableStateOf(CriarContaUIState())

    // Variável que guarda o estado das validações dos valores introduzidos
    var validacoes = mutableStateOf(false)

    // Variável utilizada com o CircularProgress Indicator, sinaliza quando o login está em progresso
    var signUpInProgress = mutableStateOf(false)

    // Funcções modificadoras das variáveis
    fun onEvent(event: CriarContaUIEvent){
        //Quando o evento acontecer,
        when(event){
            // Alteração do Nome
            is CriarContaUIEvent.NomeMudou -> {
                criarContaUIState.value = criarContaUIState.value.copy(
                    nome = event.nome

                )
                printEstado()

            }

            // Alteração do Apelido
            is CriarContaUIEvent.ApelidoMudou -> {
                criarContaUIState.value = criarContaUIState.value.copy(
                    apelido = event.apelido
                )
                printEstado()

            }

            // Alteração do Email
            is CriarContaUIEvent.EmailMudou -> {
                criarContaUIState.value = criarContaUIState.value.copy(
                    email = event.email
                )
                printEstado()

            }

            // Alteração da Password
            is CriarContaUIEvent.PasswordMudou -> {
                criarContaUIState.value = criarContaUIState.value.copy(
                    password = event.password
                )
                printEstado()

            }
            // Quando o botão de Registo é pressionado
            is CriarContaUIEvent.RegisterButtonClicked ->{
                criarConta()
            }

            // Quando a checkbox dos Termos e Condições é selecionada
            is CriarContaUIEvent.TermosCondicoesCheckBox ->{
                criarContaUIState.value = criarContaUIState.value.copy(
                    politicaTermosAceite  = event.statusValue

                )
                printEstado()

            }
        }
        // Executa a função declarada abaixo
        validarRegras()

    }

    // Função que executa o pedido de criação de conta no FireBase

    private fun criarConta() {
        Log.d(tag, "A criar conta")
        printEstado()

        cariarUtilizadorFirebase(
            email = criarContaUIState.value.email,
            password = criarContaUIState.value.password
        )

    }

    // Valida as regras definidas no ficheiro ValidarDados
    private fun validarRegras(){

        // Validar Nome
        val resValidaNome = Validar.validaNome(
            nome = criarContaUIState.value.nome
        )

        // Validar Apelido
        val resValidaApelido = Validar.validaApelido(
            apelido = criarContaUIState.value.apelido
        )

        // Validar Email
        val resValidaEmail = Validar.validaEmail(
            email = criarContaUIState.value.email
        )

        // Validar Password
        val resValidaPassword = Validar.validaPassword(
            password = criarContaUIState.value.password
        )

        // Validar ckeckbox da Politica e termos
        val resValidaPoliticaTermos = Validar.validaTermosCondicoes(
        statusValue  = criarContaUIState.value.politicaTermosAceite
        )
        Log.d(tag, "TermosCondicoes=$resValidaPoliticaTermos")

        validacoes.value =
            resValidaNome.status && resValidaApelido.status && resValidaEmail.status && resValidaPassword.status && resValidaPoliticaTermos.status

        // Logs para perceber o que acontece quando o UIState muda
        Log.d(tag, "Validar_Regras")
        Log.d(tag, "nome=$resValidaNome")
        Log.d(tag, "apelido=$resValidaApelido")
        Log.d(tag, "email=$resValidaEmail")
        Log.d(tag, "password=$resValidaPassword")
        Log.d(tag, "TermosCondicoes=$resValidaPoliticaTermos")
        Log.d(tag, "ValidaçõesOk? =${validacoes.value}")


        // Quando as regas são validadas, os valores modificados na UI são copiados para o UIState

        criarContaUIState.value = criarContaUIState.value.copy(
            erroNome = resValidaNome.status,
            erroApelido = resValidaApelido.status,
            erroEmail = resValidaEmail.status,
            erroPassword = resValidaPassword.status,
            politicaTermosErro = resValidaPoliticaTermos.status
        )

    }


    // Print no Log do que acontece com os eventos
    private fun printEstado() {
        Log.d(tag, criarContaUIState.value.toString())
    }

    // Função que cria o utilizador no Firebase
    private fun cariarUtilizadorFirebase(email: String, password: String) {

        // Mostra o CircularProgressIndicator
        signUpInProgress.value = true


        FirebaseAuth.getInstance()

            // Função do Firebase que cria o utilizador na plataforma
            .createUserWithEmailAndPassword(email, password)

            // Listener de ação completa com sucesso
            .addOnCompleteListener {
                Log.d(tag, "A tentar Registar o utilizador...")
                signUpInProgress.value = false

                if(it.isSuccessful){
                    Log.d(
                        tag,
                        "Utilizador com o email={$email} e password={$password} registado com sucesso."
                    )

                    // Atualiza o displayName do utilizador na plataforma
                    atualizarNomeUtilizadorFirebase(
                        criarContaUIState.value.nome,
                        criarContaUIState.value.apelido
                    )

                }

            }
            .addOnFailureListener {
                Log.d(tag, "Registo sem sucesso...")
                Log.d(tag, "Erro = ${it.localizedMessage}")
            }



    }

    // Atualiza o displayName do utilizador na plataforma
    private fun atualizarNomeUtilizadorFirebase(nome: String, apelido: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = "$nome $apelido"

        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(
                        tag,
                        "Utilizador com o nome={$nome} e apelido={$apelido} atualizado na plataforma. Pode fazer Login!"
                    )
                }
            }

    }


}