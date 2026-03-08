package com.inyomanw.pokemonapp.presentation.ui.detail

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inyomanw.pokemonapp.ui.component.CustomImageView


@Composable
fun DetailPokemonScreen(
    id: Int,
    viewModel: DetailPokemonViewModel
) {
    LaunchedEffect(id) {
        viewModel.getPokemonDetail(id)
    }

    val detail = viewModel.uiState
    val loading = viewModel.isLoading

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        detail?.let {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                CustomImageView(
                    url = "https://img.pokemondb.net/artwork/large/${it.name}.jpg",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f)
                        .align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "${it.name}", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Abilities:", style = MaterialTheme.typography.titleMedium)

                it.abilityList?.forEach { ability ->
                    Text("- ${ability.abilityName}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}