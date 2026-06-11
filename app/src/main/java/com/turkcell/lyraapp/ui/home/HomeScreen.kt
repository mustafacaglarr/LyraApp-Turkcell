package com.turkcell.lyraapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    // Bu ekran BNB entegrasyonunu test etmek icin bos tutulur; ana sayfa tasarimi bu kapsamda degildir.
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        // Icerik bilerek bos; istenen kapsam yalnizca BNB tasarimidir.
    }
}
