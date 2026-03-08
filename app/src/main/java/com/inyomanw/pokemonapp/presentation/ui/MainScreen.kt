package com.inyomanw.pokemonapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.inyomanw.pokemonapp.presentation.ui.home.HomeScreen
import com.inyomanw.pokemonapp.presentation.ui.home.HomeViewModel
import com.inyomanw.pokemonapp.presentation.ui.profile.ProfileScreen

@Composable
fun MainScreen(homeViewModel: HomeViewModel, onItemClick: (Int) -> Unit) {
    val navController = rememberNavController()
    val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.Profile,
    )

    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(NavigationItem.Home.route) {
                    HomeScreen(homeViewModel, onItemClick)
                }
                composable(NavigationItem.Profile.route) {
                    ProfileScreen()
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp))
                    .background(
                        color = Color(0xFF1E2329),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navigationItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) Color(0xFF6AB2F2) else Color.Gray,
                            modifier = Modifier.size(20.dp).align(alignment = Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.title,
                            color = if (isSelected) Color(0xFF6AB2F2) else Color.Gray,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )

                    }
                }
            }
        }
    }
}
