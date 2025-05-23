package com.followme.ui.screens.autenticacao.validar

object Validar {

    fun validaNome(nome: String): ResultadoValidar {
        return ResultadoValidar(
            (nome.isNotEmpty() && nome.length>=2 )
        )
    }

    fun validaApelido(apelido: String): ResultadoValidar {
        return ResultadoValidar(
            (apelido.isNotEmpty() && apelido.length>=2 )
        )

    }

    fun validaEmail(email: String): ResultadoValidar {
        return ResultadoValidar(
            (email.isNotEmpty())
        )

    }

    fun validaPassword(password: String): ResultadoValidar {
        return ResultadoValidar(
            (password.isNotEmpty() && password.length >= 6)
        )
    }

    fun validaTermosCondicoes(statusValue: Boolean): ResultadoValidar {
        return ResultadoValidar(
            statusValue
        )
    }
}

data class ResultadoValidar(
    val status : Boolean = false
)