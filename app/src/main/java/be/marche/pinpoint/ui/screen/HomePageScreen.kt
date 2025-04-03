package be.marche.pinpoint.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.marche.pinpoint.network.ConnectivityViewModel
import be.marche.pinpoint.ui.components.IconButtonWithText
import be.marche.pinpoint.ui.components.TitleWithDivider
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePageScreen(
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val connectivityViewModel: ConnectivityViewModel = koinViewModel()

    Column {
        TitleWithDivider("Homepage")
        ConnectivityStatusScreen(connectivityViewModel)
        IconButtonWithText(
            text = "Ajouter un objet",
            icon = Icons.Rounded.Add,
            onClick = {
                onClick()
            })
    }
}

@Composable
fun ConnectivityStatusScreen(viewModel: ConnectivityViewModel) {
    val isConnected by viewModel.isConnected.collectAsState()
    val capabilities by viewModel.capabilities.collectAsState()

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Capacities: " + capabilities, color = Color.Yellow, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(8.dp))
            if (isConnected) {
                Text(text = "Connected", color = Color.Green, fontSize = 24.sp)
            } else {
                Text(text = "Disconnected", color = Color.Red, fontSize = 24.sp)
            }
        }
    }
}