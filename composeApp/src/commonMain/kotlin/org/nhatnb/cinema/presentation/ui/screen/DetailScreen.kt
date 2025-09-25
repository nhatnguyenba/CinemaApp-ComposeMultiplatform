package org.nhatnb.cinema.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cinemaapp_composemultiplatform.composeapp.generated.resources.Res
import cinemaapp_composemultiplatform.composeapp.generated.resources.ic_play_circle
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.nhatnb.cinema.data.remote.api.ApiConstants
import org.nhatnb.cinema.presentation.navigation.BottomNavigationBar
import org.nhatnb.cinema.presentation.ui.theme.CategoryChipColor
import org.nhatnb.cinema.presentation.ui.theme.CinemaTypography
import org.nhatnb.cinema.presentation.ui.theme.DarkBackground
import org.nhatnb.cinema.presentation.ui.theme.PrimaryRed
import org.nhatnb.cinema.presentation.ui.theme.TextPrimary
import org.nhatnb.cinema.presentation.ui.theme.TextSecondary
import org.nhatnb.cinema.presentation.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    movieId: Long,
    currentRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = koinInject() // Reuse or create DetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Summary", "Cast & Crew", "Screenshots")

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemSelected = { item -> onNavigationItemSelected(item.route) }
            )
        }
    ) { innerPadding ->
        val movie = uiState.selectedMovie ?: return@Scaffold

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Movie Poster
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = movie.fullPosterUrl,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Back button
                    Icon(
                        painter = painterResource(Res.drawable.ic_play_circle),
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                            .clickable { onBackClick() },
                        tint = TextPrimary
                    )
                }
            }

            // Movie Info
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = CinemaTypography.headlineLarge,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "HD 4k",
                            style = CinemaTypography.labelMedium,
                            color = TextSecondary,
                            modifier = Modifier
                                .background(CategoryChipColor, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { /* Play action */ },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_play_circle),
                                contentDescription = "Play",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Play")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "2:54",
                            style = CinemaTypography.bodyMedium,
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Horror • IMDB ${movie.rating}/10",
                        style = CinemaTypography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            // Tabs
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    containerColor = DarkBackground,
                    contentColor = PrimaryRed
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
            }

            // Tab Content
            item {
                when (selectedTab) {
                    0 -> SummaryTab(movie.overview)
                    1 -> CastCrewTab()
                    2 -> ScreenshotsTab()
                }
            }
        }
    }
}

@Composable
private fun SummaryTab(summary: String) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = summary,
            style = CinemaTypography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
private fun CastCrewTab() {
    Text(
        text = "Cast & Crew information will be displayed here",
        modifier = Modifier.padding(16.dp),
        style = CinemaTypography.bodyMedium,
        color = TextSecondary
    )
}

@Composable
private fun ScreenshotsTab() {
    Text(
        text = "Screenshots will be displayed here",
        modifier = Modifier.padding(16.dp),
        style = CinemaTypography.bodyMedium,
        color = TextSecondary
    )
}