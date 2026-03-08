package com.inyomanw.pokemonapp.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val title: String, val icon: ImageVector) {
    data object Home : NavigationItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    data object Profile : NavigationItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}