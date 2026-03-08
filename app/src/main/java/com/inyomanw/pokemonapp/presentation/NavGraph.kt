package com.inyomanw.pokemonapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.inyomanw.pokemonapp.presentation.ui.MainScreen
import com.inyomanw.pokemonapp.presentation.ui.detail.DetailPokemonScreen
import com.inyomanw.pokemonapp.presentation.ui.detail.DetailPokemonViewModel
import com.inyomanw.pokemonapp.presentation.ui.home.HomeViewModel

@Composable
fun NavGraph(homeViewModel: HomeViewModel, detailPokemonViewModel: DetailPokemonViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainScreen(
                homeViewModel,
                onItemClick = { pokemonId ->
                    navController.navigate("detail/$pokemonId")
                }
            )
        }

        composable(
            "detail/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId")
            DetailPokemonScreen(pokemonId ?: 0, detailPokemonViewModel)
        }

    }
}