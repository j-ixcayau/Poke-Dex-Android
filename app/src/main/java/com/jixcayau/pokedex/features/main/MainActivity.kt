package com.jixcayau.pokedex.features.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.facebook.CallbackManager
import com.facebook.FacebookActivity
import com.jixcayau.pokedex.features.auth.login.LoginView
import com.jixcayau.pokedex.features.auth.register.RegisterView
import com.jixcayau.pokedex.features.dashboard.DashboardView
import com.jixcayau.pokedex.utils.RoutesPath

class MainActivity : ComponentActivity() {
    private var isUserLogged = false
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isUserLogged = intent.getBooleanExtra(IsUserLogged, false)

        setContent {
            View()
        }
    }

    @Composable
    private fun View() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = if (isUserLogged) RoutesPath.Dashboard else RoutesPath.Login,
        ) {
            composable(RoutesPath.Login) {
                LoginView(
                    navController = navController,
                    activity = this@MainActivity,
                    callbackManager = callbackManager,
                )
            }
            composable(RoutesPath.Register) {
                RegisterView(
                    navController = navController,
                    activity = this@MainActivity,
                    callbackManager = callbackManager,
                )
            }
            composable(RoutesPath.Dashboard) {
                DashboardView()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val IsUserLogged = "isUserLogged"
    }
}