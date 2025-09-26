package org.nhatnb.cinema.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.nhatnb.cinema.presentation.navigation.BottomNavigationBar
import org.nhatnb.cinema.presentation.ui.component.CategoryChip
import org.nhatnb.cinema.presentation.ui.component.FeaturedMovie
import org.nhatnb.cinema.presentation.ui.component.MovieCard
import org.nhatnb.cinema.presentation.ui.component.SectionHeader
import org.nhatnb.cinema.presentation.ui.theme.DarkBackground
import org.nhatnb.cinema.presentation.ui.theme.TextSecondary
import org.nhatnb.cinema.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit,
    onSeeAllClick: (String) -> Unit,
    currentRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState.popularMovies.isEmpty()) {
            viewModel.loadMovies()
        }
    }

    // Handle loading state
    if (uiState.isLoading && uiState.popularMovies.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Handle error state
    if (uiState.errorMessage != null && uiState.popularMovies.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tap to retry",
                    color = TextSecondary,
                    modifier = Modifier.clickable { viewModel.retry() }
                )
            }
        }
        return
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemSelected = { item -> onNavigationItemSelected(item.route) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBackground),
            state = rememberLazyListState()
        ) {
            // Categories Section
            item {
                SectionHeader(title = "Categories", showSeeAll = false)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                val categories = listOf("Netflix", "Action", "Adventure", "Fantasy")
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            text = category,
                            isSelected = category == "Netflix"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Top Picks Section
            item {
                SectionHeader(
                    title = "Top Picks for you",
                    onSeeAllClick = { onSeeAllClick("top_picks") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.popularMovies.take(5)) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            modifier = Modifier.width(120.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Featured Movie
            item {
                uiState.featuredMovie?.let { featuredMovie ->
                    FeaturedMovie(
                        movie = featuredMovie,
                        onClick = { onMovieClick(featuredMovie.id) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // Popular Shows Section
            item {
                SectionHeader(
                    title = "Popular shows",
                    onSeeAllClick = { onSeeAllClick("popular") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.popularMovies.chunked(2)) { rowMovies ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowMovies.forEach { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Add empty space if odd number
                    if (rowMovies.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}