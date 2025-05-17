package com.followme.ui.screens.home.entidades

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(

    val title: String,
    val description : String,
    val navigateTo: String,
    val icon : ImageVector
)