package be.marche.pinpoint.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.mars.ResultScreen
import be.marche.pinpoint.viewModel.MarsUiState
import be.marche.pinpoint.viewModel.MarsViewModel

@Composable
fun CategoryListScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val marsViewModel: MarsViewModel = viewModel()
    val marsUiState = marsViewModel.marsUiState
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        when (marsUiState) {
            is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is MarsUiState.Success -> ResultScreen(
                marsUiState.photos, modifier = modifier.fillMaxWidth()
            )

            is MarsUiState.Error -> ErrorScreen(
                marsUiState.message,
                modifier = modifier.fillMaxSize()
            )
        }

        Text("Liste catégories")

    }
}

@Composable
fun CategoryShowScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {


        Text("Show category")

    }
}