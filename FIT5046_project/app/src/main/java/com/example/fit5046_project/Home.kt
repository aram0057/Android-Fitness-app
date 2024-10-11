package com.example.fit5046_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme

//@Composable
//fun Home(navController: NavController, preferredName: String, onLogOutClick: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
//    ) {
//        Text(
//            text = "Hi $preferredName!",
//            style = MaterialTheme.typography.headlineLarge,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//        Button(
//            onClick = { navController.navigate(Routes.Track.value)},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Track your activity")
//        }
//
//        Button(
//            onClick = { navController.navigate("foodRecomendations")},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Food recomendations")
//        }
//
//        Button(
//            onClick = { navController.navigate(Routes.WorkoutRecommendation.value)},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Workout recommendations")
//        }
//
//        /* Button(
//            onClick = {
//                // Navigate to the SleepScreen when the button is clicked
//                navController.navigate(Routes.SleepScreen.value)},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//
//        ) {
//            Text(text = "View Sleep Data")
//        } */
//
//
//        Button(
//            onClick = { navController.navigate(Routes.Profile.value)},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Test")
//        }
//
//
//        Button(
//            onClick = { /* Handle Competition Updates */ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Competition Updates")
//        }
//        Button(
//            onClick = { onLogOutClick() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text(text = "Log out")
//        }
//    }
//}
@Composable
fun Home(
    navController: NavController,
    registrationViewModel: RegistrationViewModel,
    loginViewModel: LoginViewModel,
    onLogOutClick: () -> Unit
) {
    val userEmail by loginViewModel.userEmail.observeAsState("")
    val preferredName by registrationViewModel.getPreferredName(userEmail).collectAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        Text(
            text = "Hi $preferredName!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { navController.navigate(Routes.Goal.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "View Activity")
        }

        Button(
            onClick = { navController.navigate(Routes.WorkoutRecommendation.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Workout Recommendations")
        }

        Button(
            onClick = { navController.navigate(Routes.FoodRecommendation.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Food Recommendations")
        }

        Button(
            onClick = { onLogOutClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Log out")
        }
    }
}