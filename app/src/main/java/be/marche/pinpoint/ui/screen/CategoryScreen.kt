package be.marche.pinpoint.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.marche.pinpoint.sync.SyncViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.mars.ResultScreen
import be.marche.pinpoint.viewModel.MarsUiState
import be.marche.pinpoint.viewModel.MarsViewModel
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun CategoryListScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val marsViewModel: MarsViewModel = viewModel()
    val syncViewModel: SyncViewModel = koinViewModel()
    val marsUiState = marsViewModel.marsUiState
    val syncState = syncViewModel.syncUiState
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Text("Liste catégories internet")
         Spacer(Modifier.height(RallyDefaultPadding))

        when (marsUiState) {
            is MarsUiState.Pending ->  {}
            is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is MarsUiState.Success -> ResultScreen(
                marsUiState.message, modifier = modifier.fillMaxWidth()
            )
            is MarsUiState.Error -> ErrorScreen(
                marsUiState.message,
                modifier = modifier.fillMaxSize()
            )
        }
         Spacer(Modifier.height(RallyDefaultPadding))

        Text("Liste catégories db")

            when (syncState) {
                is MarsUiState.Pending ->  {}
                is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
                is MarsUiState.Success -> ResultScreen(
                    syncState.message, modifier = modifier.fillMaxWidth()
                )
                is MarsUiState.Error -> ErrorScreen(
                    syncState.message,
                    modifier = modifier.fillMaxSize()
                )
            }

        IconButton(onClick = {
            syncViewModel.sync()
        }) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(27.dp)
                    .clickable {
                 syncViewModel.sync()
                    }
            )
        }

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