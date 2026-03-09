package com.inyomanw.pokemonapp.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.ui.component.CustomImageView

@Composable
fun HomeScreen(viewModel: HomeViewModel, onItemClick: (Int) -> Unit) {

    val pokemonPagingItems = viewModel.pokemonPagingFlow.collectAsLazyPagingItems()
    var textFieldValue by remember { mutableStateOf(viewModel.searchQuery.value) }
    val searchQuery by viewModel.searchQuery.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val onSearchTriggered = {
        viewModel.searchPokemon(textFieldValue)
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Search Pokemon") },
            placeholder = { Text("Enter Pokemon Name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions {
                onSearchTriggered()
            },
            trailingIcon = {
                if (textFieldValue.isNotBlank()) {
                    IconButton(
                        onClick = {
                            textFieldValue = ""
                            viewModel.searchPokemon("")
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            }
        )

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
                        PokemonListItem(pokemon, onItemClick)
                    }
                }

                if (pokemonPagingItems.itemCount == 0
                    && pokemonPagingItems.loadState.refresh !is LoadState.Loading
                    && searchQuery.isNotBlank()
                ) {
                    item {
                        Text(
                            text = "No Pokémon found.",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.Center)
                        )
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

}

@Composable
fun PokemonListItem(pokemon: PokemonModel, onItemClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp)
            .clickable {
                onItemClick.invoke(pokemon.url.getPokemonId())
            },
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
            color = Color.Black,
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

fun String?.getPokemonId(): Int {
    return this?.let {
        split("/").last { it.isNotBlank() }.toInt()
    } ?: 0
}