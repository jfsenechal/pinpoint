package be.marche.pinpoint.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.network.ConnectivityViewModel
import be.marche.pinpoint.sync.SyncViewModel
import be.marche.pinpoint.ui.components.ConnectivityStatusScreen
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.IconButtonWithText
import be.marche.pinpoint.ui.components.LoadingScreen
import be.marche.pinpoint.ui.components.TitleWithDivider
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun SyncScreen(
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val connectivityViewModel: ConnectivityViewModel = koinViewModel()
    val isConnected by connectivityViewModel.isConnected.collectAsState()
    val capabilities by connectivityViewModel.capabilities.collectAsState()
    val syncViewModel: SyncViewModel = koinViewModel()
    val syncState = syncViewModel.syncUiState
    val context = LocalContext.current
    syncViewModel.loadItems()
    val items = syncViewModel.items

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        TitleWithDivider("Synchronisation")
        Spacer(Modifier.height(RallyDefaultPadding))
        ConnectivityStatusScreen(isConnected)
        Spacer(Modifier.height(RallyDefaultPadding))

        Text(text = "${items.count()} items en brouillons")

        Spacer(Modifier.height(RallyDefaultPadding))
        IconButtonWithText(
            text = "Synchroniser",
            isEnabled = isConnected,
            icon = Icons.Rounded.Sync,
            onClick = {
                syncViewModel.sync()
            })

        SyncStateScreen(modifier, context, syncState)
    }
}

@Composable
fun SyncStateScreen(
    modifier: Modifier = Modifier,
    context: Context,
    syncState: MarsUiState,
) {
    when (syncState) {
        is MarsUiState.Pending -> {}
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Error -> ErrorScreen(
            syncState.message,
            modifier = modifier.fillMaxSize()
        )

        is MarsUiState.Success -> {
            Toast.makeText(context, "Synchronisation ok", Toast.LENGTH_LONG).show()
        }
    }
}