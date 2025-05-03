package com.followme.data.home

import com.followme.data.login.LoginUIEvent

sealed class HomeUIEvent{

    data class displayNameChanged(val displayName:String): HomeUIEvent()


}

