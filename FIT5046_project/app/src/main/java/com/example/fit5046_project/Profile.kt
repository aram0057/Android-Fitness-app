package com.example.fit5046_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme

@Composable
fun Profile(navController: NavHostController, registrationViewModel: RegistrationViewModel, loginViewModel: LoginViewModel) {
    val userEmail by loginViewModel.userEmail.observeAsState("")
    val preferredName by registrationViewModel.getPreferredName(userEmail).collectAsState("")
    val gender by registrationViewModel.getGender(userEmail).collectAsState("")
    val age by registrationViewModel.getAge(userEmail).collectAsState("")
    val heightInt by registrationViewModel.getHeight(userEmail).collectAsState(0)
    val height = heightInt?.toString() ?: ""
    val weightInt by registrationViewModel.getWeight(userEmail).collectAsState(0)
    val weight = weightInt?.toString() ?: ""
    val desiredWeightInt by registrationViewModel.getDesiredWeight(userEmail).collectAsState(0)
    val desiredWeight = desiredWeightInt?.toString() ?: ""

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "MY PROFILE",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(36.dp))
        ProfileField("Name", preferredName, isEditMode = false) { }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileField("Gender", gender, isEditMode = false) { }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileField("Age", age, isEditMode = false) { }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileField("Height (cm)", height, isEditMode = false) { }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileField("Weight (kg)", weight, isEditMode = false) { }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileField("Desired Weight (kg)", desiredWeight, isEditMode = false) { }
        Spacer(modifier = Modifier.height(54.dp))
        Button(
            onClick = { navController.navigate(Routes.EditProfile.value) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Edit")
        }
        Button(
            onClick = { navController.navigate(Routes.Home.value) },
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text("Back")
        }
    }
}
@Composable
fun ProfileField(label: String, value: String?, isEditMode: Boolean, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Text(text = value ?: "", modifier = Modifier.weight(1f))
    }
}
@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    FIT5046_projectTheme {
        val navController = rememberNavController()
        //Profile(navController = navController)
    }
}
