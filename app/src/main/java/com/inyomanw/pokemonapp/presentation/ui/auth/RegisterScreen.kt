package com.inyomanw.pokemonapp.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inyomanw.pokemonapp.domain.model.UserModel
import com.inyomanw.pokemonapp.presentation.base.UiState
import com.inyomanw.pokemonapp.ui.component.CustomButton
import com.inyomanw.pokemonapp.ui.component.CustomOutlinedTextField

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val registerState by viewModel.registerState.collectAsState()

    val focusRequesterPassword = remember { FocusRequester() }
    val focusRequesterFullName = remember { FocusRequester() }
    val focusRequesterAddress = remember { FocusRequester() }

    LaunchedEffect(registerState) {
        if (registerState is UiState.Success) {
            viewModel.resetRegisterState()
            onRegisterSuccess()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Buat Akun Baru",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black

                )
                Text(
                    text = "Isi data di bawah untuk mendaftar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(32.dp))

                CustomOutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Username",
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions { focusRequesterPassword.requestFocus() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isPassword = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .focusRequester(focusRequesterPassword),
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions { focusRequesterFullName.requestFocus() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomOutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Full Name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .focusRequester(focusRequesterFullName),
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions { focusRequesterAddress.requestFocus() }
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomOutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Address",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .focusRequester(focusRequesterAddress),
                    singleLine = false,
                    minLines = 2,
                    maxLines = 3,
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (registerState is UiState.Error) {
                    Text(
                        text = (registerState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                val allFilled = username.isNotBlank() && password.isNotBlank()
                        && fullName.isNotBlank() && address.isNotBlank()

                CustomButton(
                    text = "Register",
                    onClick = {
                        viewModel.register(
                            user = UserModel(
                                username = username,
                                fullName = fullName,
                                address = address
                            ),
                            password = password
                        )
                    },
                    enabled = allFilled,
                    isLoading = registerState is UiState.Loading
                )
            }
        })

}
