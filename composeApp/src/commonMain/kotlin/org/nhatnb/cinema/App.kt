package org.nhatnb.cinema

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.nhatnb.cinema.presentation.ui.screen.DetailScreen
import org.nhatnb.cinema.presentation.ui.screen.HomeScreen
import org.nhatnb.cinema.presentation.ui.theme.AppTheme

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("home") }
    var selectedMovieId by remember { mutableStateOf<Long?>(null) }

    AppTheme {
        when {
            selectedMovieId != null -> {
                DetailScreen(
                    movieId = selectedMovieId!!,
                    currentRoute = currentScreen,
                    onNavigationItemSelected = { route ->
                        currentScreen = route
                        selectedMovieId = null
                    },
                    onBackClick = { selectedMovieId = null }
                )
            }

            else -> {
                HomeScreen(
                    onMovieClick = { movieId -> selectedMovieId = movieId },
                    onSeeAllClick = { section ->
                        // Handle see all click
                    },
                    currentRoute = currentScreen,
                    onNavigationItemSelected = { route -> currentScreen = route }
                )
            }
        }
    }
}