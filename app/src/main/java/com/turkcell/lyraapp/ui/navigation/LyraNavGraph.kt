package com.turkcell.lyraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.auth.login.LoginEffect
import com.turkcell.lyraapp.ui.auth.login.LoginScreen
import com.turkcell.lyraapp.ui.auth.register.RegisterEffect
import com.turkcell.lyraapp.ui.auth.register.RegisterScreen

private object LyraRoute {
    const val Login = "login"
    const val Register = "register"
}

@Composable
fun LyraNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    // Uygulama iskeleti route kararlarini tek noktada tutar; ekranlar navigation controller bilmez.
    NavHost(
        navController = navController,
        startDestination = LyraRoute.Login,
    ) {
        composable(route = LyraRoute.Login) {
            LoginScreen(
                onEffect = { effect ->
                    when (effect) {
                        LoginEffect.NavigateRegister -> navController.navigate(LyraRoute.Register)
                        LoginEffect.NavigateForgotPassword -> Unit
                        LoginEffect.ShowLoginSuccess -> Unit
                        LoginEffect.ShowLoginError -> Unit
                    }
                },
            )
        }

        composable(route = LyraRoute.Register) {
            RegisterScreen(
                onEffect = { effect ->
                    when (effect) {
                        RegisterEffect.NavigateBack -> navController.popBackStack()
                        RegisterEffect.NavigateLogin -> navController.popBackStack(
                            route = LyraRoute.Login,
                            inclusive = false,
                        )
                        RegisterEffect.ShowRegisterSuccess -> Unit
                        RegisterEffect.ShowRegisterError -> Unit
                    }
                },
            )
        }
    }
}
