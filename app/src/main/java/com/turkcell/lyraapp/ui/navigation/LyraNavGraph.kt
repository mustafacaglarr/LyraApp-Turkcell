package com.turkcell.lyraapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.auth.login.LoginEffect
import com.turkcell.lyraapp.ui.auth.login.LoginScreen
import com.turkcell.lyraapp.ui.auth.register.RegisterEffect
import com.turkcell.lyraapp.ui.auth.register.RegisterScreen
import com.turkcell.lyraapp.ui.home.HomeScreen

private object AuthRoute {
    const val Login = "login"
    const val Register = "register"
}

@Composable
fun LyraNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val mainRoutes = MainTabDestinations.map { destination -> destination.route }
    val shouldShowBottomBar = currentRoute in mainRoutes

    // Scaffold BNB'yi ana tab ekranlarinda tek noktadan sabitler; ekranlar NavController bilmez.
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                LyraBottomNavigationBar(
                    destinations = MainTabDestinations,
                    currentRoute = currentRoute,
                    onDestinationClick = { destination ->
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        // Uygulama iskeleti route kararlarini tek noktada tutar; ekranlar navigation controller bilmez.
        NavHost(
            navController = navController,
            startDestination = MainTabDestinations.first().route,
        ) {
            MainTabDestinations.forEach { destination ->
                composable(route = destination.route) {
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }

            composable(route = AuthRoute.Login) {
                LoginScreen(
                    onEffect = { effect ->
                        when (effect) {
                            LoginEffect.NavigateRegister -> navController.navigate(AuthRoute.Register)
                            LoginEffect.NavigateForgotPassword -> Unit
                            LoginEffect.ShowLoginSuccess -> Unit
                            LoginEffect.ShowLoginError -> Unit
                        }
                    },
                )
            }

            composable(route = AuthRoute.Register) {
                RegisterScreen(
                    onEffect = { effect ->
                        when (effect) {
                            RegisterEffect.NavigateBack -> navController.popBackStack()
                            RegisterEffect.NavigateLogin -> navController.popBackStack(
                                route = AuthRoute.Login,
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
}
