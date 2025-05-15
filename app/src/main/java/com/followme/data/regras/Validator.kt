package com.followme.data.regras

object Validator {

    fun validaNome(nome:String) : ValidationResult{
        return ValidationResult(
            (nome.isNotEmpty() && nome.length>=2 )
        )
    }

    fun validaApelido(apelido:String)  : ValidationResult{
        return ValidationResult(
            (apelido.isNotEmpty() && apelido.length>=2 )
        )

    }
    fun validaEmail(email:String)  : ValidationResult{
        return ValidationResult(
            (email.isNotEmpty())
        )

    }
    fun validaPassword(password:String)  : ValidationResult{
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 6)
        )
    }

    fun validaTermosCondicoes(statusValue:Boolean):ValidationResult{
        return ValidationResult(
            statusValue
        )
    }

}

data class ValidationResult(
    val status : Boolean = false
)