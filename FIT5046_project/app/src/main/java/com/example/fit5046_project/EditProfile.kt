package com.example.fit5046_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavHostController, registrationViewModel: RegistrationViewModel, loginViewModel: LoginViewModel) {
    val userEmail by loginViewModel.userEmail.observeAsState("")
    val preferredName by registrationViewModel.getPreferredName(userEmail).collectAsState("")
    val genderOptions = listOf("Male", "Female", "Others")
    val ageOptions = listOf(
        "0-10",
        "11-20",
        "21-30",
        "31-40",
        "41-50",
        "51-60",
        "61-70",
        "71-80",
        "81-90",
        "91-100"
    )
    var selectedGender by remember { mutableStateOf(genderOptions[0]) }
    var selectedAge by remember { mutableStateOf(ageOptions[0]) }
    var height by remember { mutableIntStateOf(0) }
    var weight by remember { mutableIntStateOf(0) }
    var desiredWeight by remember { mutableIntStateOf(0) }
    var isGenderMenuExpanded by remember { mutableStateOf(false) }
    var isAgeMenuExpanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(preferredName ?: "") }
    val password by registrationViewModel.getPassword(userEmail).collectAsState("")
    val confirmedPassword by registrationViewModel.getConfirmedPassword(userEmail)
        .collectAsState("")
    val Dob by registrationViewModel.getDob(userEmail).collectAsState("")
    var heightError by remember { mutableStateOf(false) }
    var weightError by remember { mutableStateOf(false) }
    var desiredWeightError by remember { mutableStateOf(false) }
    var isWeightValid by remember { mutableStateOf(true) }
    var isDesiredWeightValid by remember { mutableStateOf(true) }
    var isHeightValid by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = userEmail) {
        val heightFromDb = registrationViewModel.getHeight(userEmail).firstOrNull() ?: ""
        height = heightFromDb as Int
        val weightFromDb = registrationViewModel.getWeight(userEmail).firstOrNull() ?: ""
        weight = weightFromDb as Int
        val desiredWeightFromDb = registrationViewModel.getDesiredWeight(userEmail).firstOrNull() ?: ""
        desiredWeight = desiredWeightFromDb as Int

        val user = registrationViewModel.getUserByEmail(userEmail)
        if (user != null) {
            name = user.preferredName
            selectedGender = user.gender
            selectedAge = user.age
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "EDIT PROFILE",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("User Name") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = isGenderMenuExpanded,
            onExpandedChange = { isGenderMenuExpanded = it },
        ) {
            TextField(
                value = selectedGender,
                onValueChange = {},
                label = { Text("Select Gender") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded) },
                modifier = Modifier.menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .focusProperties { canFocus = false }
                    .padding(bottom = 8.dp),
            )

            ExposedDropdownMenu(
                expanded = isGenderMenuExpanded,
                onDismissRequest = { isGenderMenuExpanded = false }
            ) {
                genderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedGender = option
                            isGenderMenuExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = isAgeMenuExpanded,
            onExpandedChange = { isAgeMenuExpanded = it },
        ) {
            TextField(
                value = selectedAge,
                onValueChange = {},
                label = { Text("Select Age") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isAgeMenuExpanded) },
                modifier = Modifier.menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .focusProperties { canFocus = false }
                    .padding(bottom = 8.dp),
            )
            ExposedDropdownMenu(
                expanded = isAgeMenuExpanded,
                onDismissRequest = { isAgeMenuExpanded = false }
            ) {
                ageOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedAge = option
                            isAgeMenuExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        OutlinedTextField(
            value = height.toString(),
            onValueChange = { newValue ->
                height = newValue.toIntOrNull() ?: 0
                isHeightValid = height in 0..249
            },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isHeightValid) {
            Text(
                "Invalid input. Please enter a height between 0 cm and 250 cm.",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        OutlinedTextField(
            value = weight.toString(),
            onValueChange = { newValue ->
                weight = newValue.toIntOrNull() ?: 0
                isWeightValid = weight in 0..300
            },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isWeightValid) {
            Text(
                "Invalid input. Please enter a weight between 0 kg and 300 kg.",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        OutlinedTextField(
            value = desiredWeight.toString(),
            onValueChange = { newValue ->
                desiredWeight = newValue.toIntOrNull() ?: 0
                isDesiredWeightValid = desiredWeight in 0..300
            },
            label = { Text("Desired Weight (kg)") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (!isDesiredWeightValid) {
            Text(
                "Invalid input. Please enter a desired weight between 0 kg and 300 kg.",
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Button(
            onClick = {
                if (isWeightValid && isDesiredWeightValid && isHeightValid) {
                    // Save data
                    val registration = Registration(
                        userEmail ?: "",
                        password ?: "",
                        confirmedPassword ?: "",
                        Dob ?: "",
                        name,
                        selectedGender,
                        selectedAge,
                        height,
                        weight,
                        desiredWeight
                    )
                    registrationViewModel.updateRegis(registration)
                    navController.navigate(Routes.Profile.value)
                }
            },
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        ) {
            Text("Save")
        }
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        ) {
            Text("Cancel")
        }
    }
}
