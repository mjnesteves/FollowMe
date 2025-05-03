package com.followme.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.followme.screens.Home
import com.followme.screens.Login
import com.followme.screens.CriarConta
import com.followme.screens.PoliticaPrivacidade
import com.followme.screens.TermosCondicoes


/*
@Composable
fun PostOfficeApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color= Color.White
    ) {
        Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.CriarConta -> {
                CriarConta()
            }
                is Screen.PoliticaPrivacidade ->{
                PoliticaPrivacidade()
            }
                is Screen.TermosCondicoes ->{
                TermosCondicoes()
            }
                is Screen.Login ->{
                Login()
            }
                is Screen.Home -> {
                Home()
                }

            }
        }

    }

}

*/
