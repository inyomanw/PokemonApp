package com.inyomanw.pokemonapp.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inyomanw.pokemonapp.presentation.base.UiState
import com.inyomanw.pokemonapp.ui.component.CustomImageView


@Composable
fun DetailPokemonScreen(
    id: Int,
    viewModel: DetailPokemonViewModel
) {
    LaunchedEffect(id) {
        viewModel.getPokemonDetail(id)
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(content = { paddingValues ->
        when (val state = uiState) {
            UiState.Init, UiState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }

            is UiState.Success -> {
                val detail = state.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomImageView(
                        url = "https://img.pokemondb.net/artwork/large/${detail.name}.jpg",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.35f)
                            .align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${detail.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Abilities:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    detail.abilityList?.forEach { ability ->
                        Text(
                            "- ${ability.abilityName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    })
}
