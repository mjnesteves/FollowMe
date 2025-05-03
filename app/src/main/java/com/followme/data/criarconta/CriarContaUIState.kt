package com.followme.data.criarconta

data class CriarContaUIState(
    var nome: String="",
    var apelido : String = "",
    var email : String = "",
    var password : String = "",
    var politicaTermosAceite: Boolean = false,


    var erroNome : Boolean = false,
    var erroApelido : Boolean = false,
    var erroEmail : Boolean = false,
    var erroPassword : Boolean = false,
    var politicaTermosErro : Boolean = false




)