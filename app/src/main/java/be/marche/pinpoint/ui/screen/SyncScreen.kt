package be.marche.pinpoint.ui.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.data.MarsUiState
import be.marche.pinpoint.sync.SyncViewModel
import be.marche.pinpoint.ui.components.ErrorScreen
import be.marche.pinpoint.ui.components.IconButtonWithText
import be.marche.pinpoint.ui.components.LoadingScreen
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun SyncScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val syncViewModel: SyncViewModel = koinViewModel()
    val syncState = syncViewModel.syncUiState
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        Text("Synchronisation")

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

        Spacer(Modifier.height(RallyDefaultPadding))
        IconButtonWithText(
            text = "Synchroniser",
            icon = Icons.Rounded.Sync,
            onClick = {
                syncViewModel.sync()
            })
    }
}
