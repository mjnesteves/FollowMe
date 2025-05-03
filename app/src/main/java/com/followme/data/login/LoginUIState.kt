package com.followme.data.login

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.followme.navigation.Navegacao

data class LoginUIState(

    var email : String = "",
    var password : String = "",
    var loginError: Boolean = false,


    var erroEmail : Boolean = false,
    var erroPassword : Boolean = false




)