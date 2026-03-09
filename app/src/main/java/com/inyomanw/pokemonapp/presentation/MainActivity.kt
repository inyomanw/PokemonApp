package com.inyomanw.pokemonapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.inyomanw.pokemonapp.data.local.SessionManager
import com.inyomanw.pokemonapp.presentation.ui.auth.AuthViewModel
import com.inyomanw.pokemonapp.presentation.ui.detail.DetailPokemonViewModel
import com.inyomanw.pokemonapp.presentation.ui.home.HomeViewModel
import com.inyomanw.pokemonapp.presentation.ui.profile.ProfileViewModel
import com.inyomanw.pokemonapp.ui.theme.PokemonAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private val homeViewModel: HomeViewModel by viewModels()
    private val detailPokemonViewModel: DetailPokemonViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                NavGraph(
                    homeViewModel = homeViewModel,
                    detailPokemonViewModel = detailPokemonViewModel,
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    isLoggedIn = sessionManager.isLoggedIn()
                )
            }
        }
    }
}
