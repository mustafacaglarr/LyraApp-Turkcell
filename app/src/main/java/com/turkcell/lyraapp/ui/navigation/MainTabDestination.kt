package com.turkcell.lyraapp.ui.navigation

import androidx.compose.runtime.Immutable

@Immutable
data class MainTabDestination(
    val route: String,
    val label: String,
    val icon: MainTabIcon,
)

enum class MainTabIcon {
    Home,
    Search,
    Library,
    Favorite,
    Profile,
}

// BNB ekranlari tek listede tutulur; NavGraph ve bottom bar ayni route kaynagini kullanir.
val MainTabDestinations = listOf(
    MainTabDestination(
        route = "home",
        label = "Ana sayfa",
        icon = MainTabIcon.Home,
    ),
    MainTabDestination(
        route = "search",
        label = "Ara",
        icon = MainTabIcon.Search,
    ),
    MainTabDestination(
        route = "library",
        label = "Kütüphane",
        icon = MainTabIcon.Library,
    ),
    MainTabDestination(
        route = "favorites",
        label = "Favoriler",
        icon = MainTabIcon.Favorite,
    ),
    MainTabDestination(
        route = "profile",
        label = "Profil",
        icon = MainTabIcon.Profile,
    ),
)
