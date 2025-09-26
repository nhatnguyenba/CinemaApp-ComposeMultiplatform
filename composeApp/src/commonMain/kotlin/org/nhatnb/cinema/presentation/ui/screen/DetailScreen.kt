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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import org.nhatnb.cinema.presentation.navigation.BottomNavigationBar
import org.nhatnb.cinema.presentation.ui.component.ChipType
import org.nhatnb.cinema.presentation.ui.component.QualityChips
import org.nhatnb.cinema.presentation.ui.theme.DarkBackground
import org.nhatnb.cinema.presentation.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    movieId: Long,
    currentRoute: String,
    onNavigationItemSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Summary", "Cast & Crew", "Screenshots")

    LaunchedEffect(movieId) {
        if (uiState.selectedMovie?.id != movieId) {
            viewModel.loadMovieDetail(movieId)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemSelected = { item -> onNavigationItemSelected(item.route) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBackground)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingState()
                }

                uiState.errorMessage != null -> {
                    ErrorState(
                        errorMessage = uiState.errorMessage!!,
                        onRetry = { viewModel.loadMovieDetail(movieId) },
                        onBackClick = onBackClick
                    )
                }

                uiState.selectedMovie == null -> {
                    EmptyState(onBackClick = onBackClick)
                }

                else -> {
                    MovieDetailContent(
                        movie = uiState.selectedMovie!!,
                        selectedTab = selectedTab,
                        tabs = tabs,
                        onTabSelected = { selectedTab = it },
                        onBackClick = onBackClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading movie details...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorState(
    errorMessage: String,
    onRetry: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Back button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { onBackClick() }
                    .padding(bottom = 16.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "❌",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Try Again")
            }
        }
    }
}

@Composable
private fun EmptyState(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Back button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { onBackClick() }
                    .padding(bottom = 16.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "🎬",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Movie not found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "The movie you're looking for doesn't exist or has been removed.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MovieDetailContent(
    movie: org.nhatnb.cinema.domain.model.Movie,
    selectedTab: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Movie Poster Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = movie.fullBackdropUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

//                // Gradient overlay
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp)
//                        .background(
//                            androidx.compose.ui.graphics.Brush.verticalGradient(
//                                colors = listOf(
//                                    androidx.compose.ui.graphics.Color.Transparent,
//                                    DarkBackground
//                                ),
//                                startY = 300f / 3,
//                                endY = 300f
//                            )
//                        )
//                )

                // Back button
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                        .clickable { onBackClick() },
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Movie Info Section
        item {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    QualityChips(chipType = ChipType.MEDIUM)

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { /* Play action */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "2:54",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Horror • IMDB ${movie.rating}/10 • ${movie.releaseYear}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Tabs Section
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.padding(horizontal = 16.dp),
                containerColor = DarkBackground,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { onTabSelected(index) }
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

@Composable
private fun SummaryTab(summary: String) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = summary.ifEmpty {
                "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, discovered the undoubtable source."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
private fun CastCrewTab() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        // Sample cast members
        val castMembers = listOf(
            "Robert Pattinson as Bruce Wayne / Batman",
            "Zoë Kravitz as Selina Kyle / Catwoman",
            "Jeffrey Wright as James Gordon",
            "Colin Farrell as Oswald Cobblepot / Penguin"
        )

        castMembers.forEach { member ->
            Text(
                text = "• $member",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun ScreenshotsTab() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "Movie screenshots will be displayed here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        // Placeholder for screenshots grid
//        LazyColumn {
//            items(4) { index ->
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .background(MaterialTheme.colorScheme.surfaceVariant)
//                        .clip(RoundedCornerShape(8.dp))
//                        .padding(8.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Screenshot ${index + 1}",
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
    }
}