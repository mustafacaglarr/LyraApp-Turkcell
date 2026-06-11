package com.turkcell.lyraapp.ui.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LyraBottomNavigationBar(
    destinations: List<MainTabDestination>,
    currentRoute: String?,
    onDestinationClick: (MainTabDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    // BNB tek komponette tutulur; ana tab ekranlari degisse de alt navigasyon ayni kalir.
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 18.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        destinations.forEach { destination ->
            val selected = destination.route == currentRoute

            BottomNavigationItem(
                destination = destination,
                selected = selected,
                onClick = { onDestinationClick(destination) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun BottomNavigationItem(
    destination: MainTabDestination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = modifier
            .height(54.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Secili ikon zemini referans BNB'deki pill vurgusunu verir.
        Box(
            modifier = Modifier
                .size(width = 58.dp, height = 28.dp)
                .background(
                    color = if (selected) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.52f)
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            MainTabIconContent(
                icon = destination.icon,
                color = contentColor,
                modifier = Modifier.size(22.dp),
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = destination.label,
            color = contentColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MainTabIconContent(
    icon: MainTabIcon,
    color: Color,
    modifier: Modifier = Modifier,
) {
    when (icon) {
        MainTabIcon.Home -> HomeIcon(color = color, modifier = modifier)
        MainTabIcon.Search -> SearchIcon(color = color, modifier = modifier)
        MainTabIcon.Library -> LibraryIcon(color = color, modifier = modifier)
        MainTabIcon.Favorite -> FavoriteIcon(color = color, modifier = modifier)
        MainTabIcon.Profile -> ProfileIcon(color = color, modifier = modifier)
    }
}

@Composable
private fun HomeIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        drawLine(color, Offset(size.width * 0.18f, size.height * 0.48f), Offset(size.width * 0.5f, size.height * 0.18f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.5f, size.height * 0.18f), Offset(size.width * 0.82f, size.height * 0.48f), strokeWidth, StrokeCap.Round)
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.28f, size.height * 0.44f),
            size = Size(size.width * 0.44f, size.height * 0.38f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth),
        )
    }
}

@Composable
private fun SearchIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        drawCircle(
            color = color,
            radius = size.minDimension * 0.25f,
            center = Offset(size.width * 0.43f, size.height * 0.42f),
            style = Stroke(width = strokeWidth),
        )
        drawLine(color, Offset(size.width * 0.61f, size.height * 0.60f), Offset(size.width * 0.80f, size.height * 0.79f), strokeWidth, StrokeCap.Round)
    }
}

@Composable
private fun LibraryIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 1.8.dp.toPx()
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.18f, size.height * 0.18f),
            size = Size(size.width * 0.64f, size.height * 0.64f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth),
        )
        drawLine(color, Offset(size.width * 0.38f, size.height * 0.30f), Offset(size.width * 0.38f, size.height * 0.70f), strokeWidth, StrokeCap.Round)
        drawLine(color, Offset(size.width * 0.56f, size.height * 0.30f), Offset(size.width * 0.56f, size.height * 0.70f), strokeWidth, StrokeCap.Round)
        drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(size.width * 0.62f, size.height * 0.62f))
    }
}

@Composable
private fun FavoriteIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val points = listOf(
            Offset(size.width * 0.50f, size.height * 0.80f),
            Offset(size.width * 0.22f, size.height * 0.50f),
            Offset(size.width * 0.26f, size.height * 0.28f),
            Offset(size.width * 0.50f, size.height * 0.38f),
            Offset(size.width * 0.74f, size.height * 0.28f),
            Offset(size.width * 0.78f, size.height * 0.50f),
            Offset(size.width * 0.50f, size.height * 0.80f),
        )
        points.zipWithNext().forEach { (start, end) ->
            drawLine(color, start, end, strokeWidth, StrokeCap.Round)
        }
    }
}

@Composable
private fun ProfileIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        drawCircle(
            color = color,
            radius = size.minDimension * 0.15f,
            center = Offset(size.width * 0.50f, size.height * 0.34f),
            style = Stroke(width = strokeWidth),
        )
        drawArc(
            color = color,
            startAngle = 205f,
            sweepAngle = 130f,
            useCenter = false,
            topLeft = Offset(size.width * 0.24f, size.height * 0.50f),
            size = Size(size.width * 0.52f, size.height * 0.42f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
        )
    }
}
