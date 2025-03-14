package be.marche.pinpoint.ui.mars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.R
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.theme.PinPointTheme
import be.marche.pinpoint.viewModel.MarsUiState

@Composable
fun MarsScreen(
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (marsUiState) {
        is MarsUiState.Pending ->  {}
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> ResultScreen(
            marsUiState.message, modifier = modifier.fillMaxWidth()
        )

        is MarsUiState.Error -> ErrorScreen(marsUiState.message, modifier = modifier.fillMaxSize())
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    PinPointTheme {
        ResultScreen(stringResource(R.string.placeholder_success))
    }
}