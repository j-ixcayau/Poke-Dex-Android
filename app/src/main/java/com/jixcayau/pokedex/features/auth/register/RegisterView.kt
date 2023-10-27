package com.jixcayau.pokedex.features.auth.register


import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.facebook.CallbackManager
import com.jixcayau.pokedex.R
import com.jixcayau.pokedex.composables.HorizontalSpace
import com.jixcayau.pokedex.composables.Input
import com.jixcayau.pokedex.composables.InputType
import com.jixcayau.pokedex.composables.Label
import com.jixcayau.pokedex.composables.LabelType
import com.jixcayau.pokedex.composables.PokeButton
import com.jixcayau.pokedex.composables.VerticalSpace
import com.jixcayau.pokedex.features.auth.composables.AuthBody
import com.jixcayau.pokedex.features.auth.composables.SocialButton
import com.jixcayau.pokedex.utils.AppSpaces
import com.jixcayau.pokedex.utils.RoutesPath
import com.jixcayau.pokedex.utils.auth.FacebookAuthManager
import com.jixcayau.pokedex.utils.auth.GoogleAuthManager

@Composable
fun RegisterView(
    navController: NavHostController,
    activity: Activity,
    callbackManager: CallbackManager,
) {
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    // Google Auth
    val googleAuthManager by remember {
        mutableStateOf(GoogleAuthManager(context))
    }
    val googleLauncher = googleAuthManager.rememberFirebaseAuthLauncher(
        onAuthComplete = {
            navigateToDashboard(navController)
        },
        onAuthError = {
            Log.d("error", "error")
        },
    )

    // Facebook Auth
    val facebookAuthManager by remember {
        mutableStateOf(FacebookAuthManager(activity, callbackManager))
    }
    val facebookScope = rememberCoroutineScope()


    val viewModel = remember {
        RegisterViewModel()
    }

    if (viewModel.customLogged) {
        viewModel.customLogged = false
        navigateToDashboard(navController)
    }

    AuthBody(
        isLoading = false,
        onBackTap = {
            navController.popBackStack()
        },
        actionTitle = stringResource(R.string.already_have_an_account),
        actionButtonText = stringResource(R.string.login),
        actionOnTap = {
            navController.popBackStack()
        },
        content = {
            VerticalSpace(AppSpaces.l)

            Label(
                value = stringResource(R.string.register_title),
                type = LabelType.Title,
            )

            VerticalSpace(AppSpaces.s)

            Label(
                value = stringResource(R.string.register_subtitle),
                type = LabelType.Subtitle,
            )

            VerticalSpace(AppSpaces.xl)

            Input(
                value = emailValue,
                title = R.string.email,
                type = InputType.Email,
            )

            VerticalSpace(AppSpaces.s)

            Input(
                value = passwordValue,
                title = R.string.password,
                type = InputType.Password,
                imeAction = ImeAction.Done
            )

            VerticalSpace(AppSpaces.m)

            PokeButton(
                text = stringResource(R.string.sign_in_button),
                onTap = {
                    focusManager.clearFocus()

                    viewModel.register(emailValue.value, passwordValue.value)
                },
            )

            VerticalSpace(AppSpaces.m)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                SocialButton(
                    iconId = R.drawable.ic_facebook_logo,
                    onTap = {
                        facebookAuthManager.launch(
                            onAuthComplete = {
                                navigateToDashboard(navController)
                            },
                            onAuthError = {

                            },
                            scope = facebookScope
                        )
                    },
                )

                HorizontalSpace(AppSpaces.m)

                SocialButton(
                    iconId = R.drawable.ic_google_logo,
                    onTap = {
                        googleLauncher.launch(googleAuthManager.getIntent())
                    },
                )
            }

            VerticalSpace(AppSpaces.m)
        },
    )
}

private fun navigateToDashboard(navController: NavHostController) {
    navController.navigate(RoutesPath.Dashboard) {
        popUpTo(RoutesPath.Login) {
            inclusive = true
        }
    }
}