package com.example.movieez.ui.movies.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.movies.details.MovieDetailsViewModel
import coil.compose.AsyncImage
import com.example.movieez.R
import com.example.presentation.movies.details.LoadMovieDetailsIntent
import com.example.presentation.movies.details.MovieDetailsState

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setIntent(LoadMovieDetailsIntent)
    }

    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        when (val state = uiState) {
            MovieDetailsState.Error -> {
                Text(text = stringResource(R.string.error_message))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { viewModel.setIntent(LoadMovieDetailsIntent) }) {
                    Text(text = stringResource(R.string.retry))
                }
            }

            is MovieDetailsState.Idle -> {
                val movie = state.movie
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    model = movie.posterUrl,
                    contentDescription = stringResource(id = R.string.movie_poster_content_desc),
                    placeholder = ColorPainter(Color.Gray),
                    error = ColorPainter(Color.Gray)
                )

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = movie.overview)
            }

            MovieDetailsState.Loading -> CircularProgressIndicator()
        }
    }

}