package com.followme.data.home

sealed class HomeUIEvent{

    data class displayNameChanged(val displayName:String): HomeUIEvent()

}

