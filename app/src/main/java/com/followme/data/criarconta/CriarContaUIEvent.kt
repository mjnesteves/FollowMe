package com.followme.data.criarconta

sealed class CriarContaUIEvent{

    data class NomeChanged(val nome:String): CriarContaUIEvent()
    data class ApelidoChanged(val apelido:String): CriarContaUIEvent()
    data class EmailChanged(val email:String): CriarContaUIEvent()
    data class PasswordChanged(val password:String): CriarContaUIEvent()
    data class TermosCondicoesCheckBox(val statusValue: Boolean): CriarContaUIEvent()


    object RegisterButtonClicked : CriarContaUIEvent()


}
