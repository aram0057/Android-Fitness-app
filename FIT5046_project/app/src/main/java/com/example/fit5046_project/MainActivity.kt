package com.example.fit5046_project

import RegisterScreen
import RegisterSuccessfulScreen
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fit5046_project.ui.theme.FIT5046_projectTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialise Google Sign-In client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                handleGoogleSignInResult(data)
            } else {
                // Handle the case where sign-in was not successful
                Log.w(TAG, "Google sign in failed: Result code ${result.resultCode}")
            }
        }

        val registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        val registrationRepository = RegistrationRepository(application = application)
        val loginViewModel = LoginViewModel(repository = registrationRepository)

        setContent {
            navController = rememberNavController()
            FIT5046_projectTheme {

                NavGraph(
                    navController = navController,
                    registrationViewModel = registrationViewModel,
                    loginViewModel = loginViewModel,
                    performGoogleSignIn = this::performGoogleSignIn
                )
            }
        }
    }

    private fun performGoogleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }


    private fun handleGoogleSignInResult(data: Intent?) {
        if (data != null && ::navController.isInitialized) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                navController.navigate(Routes.Home.value)

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                navController.navigate(Routes.Welcome.value)
            }
        } else {
            Log.w(TAG, "Google sign in failed: Intent data is null or navController is not initialized")
        }
    }

    companion object {
        private const val TAG = "GoogleSignInActivity"
    }
}

@RequiresApi(64)
@Composable
fun NavGraph(
    navController: NavController,
    registrationViewModel: RegistrationViewModel,
    loginViewModel: LoginViewModel,
    performGoogleSignIn: () -> Unit, ) {
    val userEmail by loginViewModel.userEmail.observeAsState("")
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, userEmail)
        }
    )
    {
        NavHost(
            navController = navController as NavHostController,
            startDestination = Routes.Welcome.value
        ) {
            composable(Routes.Home.value) {
                Home(navController, registrationViewModel, loginViewModel) {
                    navController.navigate(Routes.LogoutScreen.value)
                }
            }

            composable(Routes.Profile.value) {
                Profile(navController, registrationViewModel, loginViewModel)
            }

            composable(Routes.EditProfile.value) {
                EditProfile(navController, registrationViewModel, loginViewModel)
            }


            composable(Routes.Track.value) {
                Track(navController)
            }

            composable(Routes.Goal.value) {
                Goal(navController)
            }

            composable(Routes.LogoutScreen.value) {
                LogoutScreen(navController)
            }

            composable(Routes.Welcome.value) {
                Welcome(navController)
            }

            composable(Routes.LoginScreen.value) {
                LoginScreen(
                    viewModel = loginViewModel,
                    registrationRepository = RegistrationRepository(application = Application()),
                    onLoginClick = { email, password->
                        loginViewModel.viewModelScope.launch {
                            loginViewModel.setUserEmail(email)
                            navController.navigate(Routes.Home.value)
                        }
                    },
                    onRegisterClick = { navController.navigate(Routes.RegisterScreen.value) },
                    onBackClick = { navController.navigate(Routes.Welcome.value) },
                    onGoogleSignInClick = performGoogleSignIn
                )
            }

            composable(Routes.RegisterScreen.value) {
                RegisterScreen(
                    registrationViewModel = registrationViewModel,
                    registrationRepository = RegistrationRepository(application = Application()),
                    onRegisterClick = { email, password, confirmPassword, dob, preferredName ->
                        navController.navigate(Routes.RegisterSuccessfulScreen.value)
                    },
                    onBackPressed = {
                        navController.navigate(Routes.Welcome.value)
                    }
                )
            }

            composable(Routes.RegisterSuccessfulScreen.value) {
                RegisterSuccessfulScreen(
                    onNavigateToLogin = { navController.navigate(Routes.LoginScreen.value) },
                    onBackPressed = { navController.navigate(Routes.Welcome.value) }
                )
            }

            composable(Routes.WorkoutRecommendation.value) {
                val workoutViewModel = viewModel<WorkoutRecommendationViewModel>()
                WorkoutRecommendation(navController = navController, viewModel = workoutViewModel)
            }

            composable(Routes.FoodRecommendation.value) {
                DropdownListWithRecommendation()
            }
        }
    }
}
