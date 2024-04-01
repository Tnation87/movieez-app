package com.example.movieez.ui.movies.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.presentation.movies.models.MovieUiModel
import coil.compose.AsyncImage
import com.example.movieez.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.movies.list.MoviesListViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.movies.list.MoviesListIntent

@Composable
fun MoviesListScreen(
    viewModel: MoviesListViewModel = hiltViewModel(),
    onClickOnMovie: (Int) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyColumnState = rememberLazyListState()
    val lastVisibleIndex by remember {
        derivedStateOf {
            lazyColumnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 1
        }
    }

    LaunchedEffect(lastVisibleIndex) {
        if (uiState.hasReachedEnd.not() && lastVisibleIndex >= uiState.indexOfLastScrollItemWithPadding)
            viewModel.setIntent(MoviesListIntent.GetNextPageIntent)
    }

    DisposableEffect(uiState.isError) {
        if (uiState.isError)
            Toast.makeText(context, R.string.error_message, Toast.LENGTH_LONG).show()

        onDispose {
            viewModel.setIntent(MoviesListIntent.ConsumeErrorIntent)
        }
    }

    LazyColumn(state = lazyColumnState) {
        items(items = uiState.movies) { movie ->
            MovieListItem(movie = movie, onClick = onClickOnMovie)
        }

        if (uiState.isLoadingNewItems)
            item {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                )
            }
    }
}

@Composable
fun MovieListItem(movie: MovieUiModel, onClick: (Int) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(movie.id) }
            .padding(16.dp)
    ) {
        val (image, title, releaseDate) = createRefs()
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                },
            model = movie.posterUrl,
            contentDescription = stringResource(id = R.string.movie_poster_content_desc),
            placeholder = ColorPainter(Color.Gray),
            error = ColorPainter(Color.Gray)
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end, 8.dp)
                end.linkTo(parent.end)
                top.linkTo(image.top)
                width = Dimension.fillToConstraints
            })

        Text(
            text = movie.releaseDate,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.constrainAs(releaseDate) {
                start.linkTo(title.start)
                top.linkTo(title.bottom, 8.dp)
            })
    }
}
