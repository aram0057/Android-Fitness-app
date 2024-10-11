package com.example.fit5046_project

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
private val EMAIL_KEY = stringPreferencesKey("email")
private val PASSWORD_KEY = stringPreferencesKey("password")
private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")



@OptIn(UnstableApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    registrationRepository: RegistrationRepository,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    onGoogleSignInClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val dataStore = LocalContext.current.dataStore

    LaunchedEffect(Unit) {
        try {
            val userPreferences = dataStore.data.first()
            email = userPreferences[EMAIL_KEY] ?: ""
            password = userPreferences[PASSWORD_KEY] ?: ""
            rememberMe = userPreferences[REMEMBER_ME_KEY] ?: false
        } catch (e: Exception) {
            android.util.Log.e("LoginScreen", "Error reading data from DataStore: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LOG IN",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.isNotEmpty(),
            singleLine = true
        )
        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.isNotEmpty(),
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
                ) {
                    val icon =
                        if (passwordVisibility) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    Icon(icon, contentDescription = "Toggle password visibility")
                }
            }
        )
        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { isChecked ->
                    rememberMe = isChecked
                    if (!isChecked) {
                        // Clear the saved email if Remember Me is unchecked
                        email = ""
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Remember Me")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val isValid = validateInputs(email, password)
                if (isValid) {
                    // Launch a coroutine to handle login logic
                    coroutineScope.launch {
                        // Check if the email is registered
                        val isEmailRegistered = viewModel.isEmailRegistered(email)
                        if (!isEmailRegistered) {
                            emailError = "Email not registered"
                            // Update error messages if the inputs are not valid
                            passwordError = if (password.isEmpty()) "Password is required" else ""
                        } else {
                            // Reset the emailError message if the email is registered
                            emailError = ""

                            // If the email is registered, check if the password is correct
                            val isPasswordCorrect = viewModel.isPasswordCorrect(email, password)
                            if (isPasswordCorrect) {
                                // If the password is correct, invoke the onLoginClick callback
                                onLoginClick(email, password)
                            } else {
                                // If the password is incorrect, update the passwordError message
                                passwordError = "Invalid password."
                            }
                        }
                        if (rememberMe) {
                            dataStore.edit { preferences ->
                                preferences[EMAIL_KEY] = email
                                preferences[PASSWORD_KEY] = password
                                preferences[REMEMBER_ME_KEY] = rememberMe
                            }
                        }
                    }
                }  else {
                    // Update error messages if the inputs are not valid
                    emailError = if (email.isEmpty()) "Email is required" else ""
                    passwordError = if (password.isEmpty()) "Password is required" else ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onGoogleSignInClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign in with Google")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onBackClick() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(
                onClick = { onRegisterClick() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Register")
            }
        }
    }
}
fun validateInputs(email: String, password: String): Boolean {
    return email.isNotBlank() && password.isNotBlank()
}
