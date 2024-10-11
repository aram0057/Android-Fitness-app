
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fit5046_project.Registration
import com.example.fit5046_project.RegistrationRepository
import com.example.fit5046_project.RegistrationViewModel
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun RegisterSuccessfulScreen(onNavigateToLogin: () -> Unit, onBackPressed: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registration Successful!")
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onBackPressed) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onNavigateToLogin) {
                Text("Login")
            }
        }
    }
}

@RequiresApi(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    registrationViewModel: RegistrationViewModel,
    registrationRepository: RegistrationRepository,
    onRegisterClick: (String, String, String, String, String) -> Unit,
    onBackPressed: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var preferredName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf(0) }
    var weight by remember { mutableStateOf(0) }
    var desiredWeight by remember { mutableStateOf(0) }
    var passwordVisibility by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var dobError by remember { mutableStateOf("") }
    var isEmailRegistered by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val calendar = Calendar.getInstance()
    val today = Calendar.getInstance().timeInMillis
    calendar.set(2024, 0, 1) // month (0) is January
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today
    )

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf(calendar.timeInMillis)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "REGISTER",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium
        )
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
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
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
        if (confirmPasswordError.isNotEmpty()) {
            Text(
                text = confirmPasswordError,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showDatePicker) {
            val selectedDate = datePickerState.selectedDateMillis ?: today
            if (selectedDate > today) {
                dobError = "Please select a date on or before today."
            } else {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                dob = sdf.format(Date(selectedDate))
                dobError = ""
            }

            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedDate = datePickerState.selectedDateMillis ?: today
                        if (selectedDate <= today) {
                            // Update the dob variable with the selected date
                            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dob = sdf.format(Date(selectedDate))
                            dobError = ""
                        } else {
                            dobError = "Please select a date on or before today."
                        }
                        showDatePicker =
                            dobError.isNotEmpty()
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }

        Button(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(text = "Enter Date of Birth")
        }

        if (dobError.isNotEmpty()) {
            Text(
                text = dobError,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        OutlinedTextField(
            value = preferredName,
            onValueChange = { preferredName = it },
            label = { Text("Preferred Name") },
            modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(Unit) {
            if (email.isNotBlank()) {
                isEmailRegistered = registrationRepository.isEmailRegistered(email)
                if (isEmailRegistered) {
                    emailError = "Email is already registered"
                } else {
                    emailError = ""
                }
            } else {
                emailError = ""
            }
        }

        Button(
            onClick = {
                // Validate inputs before registering
                val validationResult =
                    validateInputs(email, password, confirmPassword, dob, registrationRepository)
                if (validationResult.isValid) {
                    // Launch a coroutine to check if email is already registered
                    coroutineScope.launch {
                        isEmailRegistered = registrationRepository.isEmailRegistered(email)
                        if (isEmailRegistered) {
                            emailError = "Email is already registered"
                        } else {
                            // Inputs are valid, proceed with registration
                            val registration = Registration(email, password, confirmPassword, dob, preferredName, gender, age, height, weight, desiredWeight)
                            registrationViewModel.insertRegis(registration)
                            // Call the provided callback to navigate to the next screen
                            onRegisterClick(email, password, confirmPassword, dob, preferredName)
                        }
                    }
                } else {
                    // Inputs are not valid, update error messages
                    emailError = validationResult.emailError
                    passwordError = validationResult.passwordError
                    confirmPasswordError = validationResult.confirmPasswordError
                    dobError = validationResult.dobError
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
        Button(
            onClick = {
                onBackPressed()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cancel")
        }
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val dobError: String = ""
)

fun validateInputs(
    email: String,
    password: String,
    confirmPassword: String,
    dob: String,
    registrationRepository: RegistrationRepository

): ValidationResult {
    var emailError = ""
    var passwordError = ""
    var confirmPasswordError = ""
    var dobError = ""

    // Email validation
    if (email.isBlank()) {
        emailError = "Email is required"
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        emailError = "Invalid email address"
    }

    // Password validation
    if (password.isBlank()) {
        passwordError = "Password is required"
    } else if (password.length < 6) {
        passwordError = "Password must be at least 6 characters"
    } else if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
        passwordError =
            "Password must include at least one uppercase letter, one lowercase letter, and one number"
    }

    // Confirm password validation
    if (confirmPassword.isBlank()) {
        confirmPasswordError = "Confirm password is required"
    } else if (confirmPassword != password) {
        confirmPasswordError = "Passwords do not match"
    }

    // Date of birth validation
    if (dob.isBlank()) {
        dobError = "Date of birth is required"
    }

    // Determine if the inputs are valid
    val isValid =
        emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty() && dobError.isEmpty()
    return ValidationResult(isValid, emailError, passwordError, confirmPasswordError, dobError)
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    FIT5046_projectTheme {

    }
}

