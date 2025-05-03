package com.followme.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel () {


    private val TAG= HomeViewModel::class.simpleName

    var HomeUIState = mutableStateOf(HomeUIState())

    private fun  onEvent(event: HomeUIEvent){

        when(event){
            is HomeUIEvent.displayNameChanged ->{
                HomeUIState.value = HomeUIState.value.copy(
                    displayName = event.displayName
                )
            }

        }

    }




private fun getUserData() {
    FirebaseAuth.getInstance().currentUser?.also {
        it.displayName?.also { displayName ->
            onEvent(HomeUIEvent.displayNameChanged(displayName))
        }
    }
}


fun getDisplayName():String {
    //getUserData()
    return  HomeUIState.value.displayName
}




    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            itemId = "SettingsScreen"
        ),

        NavigationItem(
            title = "Info",
            icon = Icons.Default.Info,
            description = "Information Screen",
            itemId = "InfoScreen"
        ),


        )



fun terminarSessao(navController:NavController){
    val firebaseAuth = FirebaseAuth.getInstance()

    firebaseAuth.signOut()

    val authStateListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser == null) {
            Log.d(TAG, "Inside signout sucess")
            navController.navigate("Login")
        } else {
            Log.d(TAG, "Inside signout is not complete")
        }
    }

    firebaseAuth.addAuthStateListener ( authStateListener )
}


}

