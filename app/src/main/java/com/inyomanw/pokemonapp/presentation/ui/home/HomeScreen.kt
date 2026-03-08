package com.inyomanw.pokemonapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.ui.component.CustomImageView

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val pokemonPagingItems = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(
                count = pokemonPagingItems.itemCount,
                key = pokemonPagingItems.itemKey { it.name ?: "" }
            ) { index ->
                val pokemon = pokemonPagingItems[index]
                if (pokemon != null) {
                    PokemonListItem(pokemon = pokemon)
                }
            }

            when (val state = pokemonPagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        ErrorState(
                            message = state.error.message ?: "Unknown Error",
                            onRetry = { pokemonPagingItems.retry() })
                    }
                }

                else -> {}
            }

            when (val state = pokemonPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(2) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(2) }) {
                        ErrorState(
                            message = state.error.message ?: "Unknown Error",
                            onRetry = { pokemonPagingItems.retry() })
                    }
                }

                else -> {}
            }
        }
    }

}

@Composable
fun PokemonListItem(pokemon: PokemonModel) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomImageView(
            url = "https://img.pokemondb.net/artwork/large/${pokemon.name}.jpg",
            modifier = Modifier
                .aspectRatio(4f / 3f)
        )
        Text(
            text = pokemon.name ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = "Try Again")
        }
    }
}
