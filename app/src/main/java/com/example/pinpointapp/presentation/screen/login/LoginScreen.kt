package com.example.pinpointapp.presentation.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.pinpointapp.navigation.Screen
import com.example.pinpointapp.presentation.screen.StartActivityForResult
import com.example.pinpointapp.presentation.screen.signIn

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val signedInState = loginViewModel.signedInState
    val messageBarState = loginViewModel.messageBarState

    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = {
            LoginTopBar()
        },
        content = {
            LoginContent(
                signedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.updateSignedInState(signedIn = true)
                }
            )
        }
    )

    StartActivityForResult(
        key = signedInState,
        onResultReceived = { accessToken ->
            Backendless.UserService.loginWithOAuth2(
                "googleplus",
                accessToken,
                mutableMapOf(
                    "email" to "email",
                    "given_name" to "first_name",
                    "family_name" to "last_name",
                    "picture" to "profile_photo"
                ),
                object : AsyncCallback<BackendlessUser> {
                    val tag = "LoginScreen"
                    override fun handleResponse(response: BackendlessUser?) {
                        Log.d(tag, response.toString())
                        navController.popBackStack()
                        navController.navigate(Screen.Map.route)
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        loginViewModel.updateSignedInState(signedIn = false)
                        if (fault != null) {
                            loginViewModel.updateMessageBarState(message = fault.message)
                        }
                    }
                },
                false
            )
        },
        onDialogDismissed = {
            loginViewModel.updateSignedInState(signedIn = false)
            loginViewModel.updateMessageBarState(message = it)
        },
        launcher = { activityLauncher ->
            if (signedInState) {
                signIn(activity = activity, launcher = activityLauncher)
            }
        })
}