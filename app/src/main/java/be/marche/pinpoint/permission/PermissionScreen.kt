package be.marche.pinpoint.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@Composable
fun CameraScreen(modifier: Modifier) {

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequiredPermission() {
    val state = rememberPermissionState(Manifest.permission.CAMERA)
    Scaffold { paddingValues ->
        when {
            state.status.isGranted -> CameraScreen(modifier = Modifier.padding(paddingValues))
            else -> {
                LaunchedEffect(Unit) {
                    state.launchPermissionRequest()
                }
                PermissionRationale(state)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRationale(state: PermissionState) {
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.padding(vertical = 120.dp, horizontal = 16.dp)) {
            Icon(
                Icons.Rounded.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text("Camera permission required", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(4.dp))
            Text("This is required in order for the app to take pictures")
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        ) {
            Text("Go to settings")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OptionalPermissionScreen() {
    val context = LocalContext.current.applicationContext
    val state = rememberPermissionState(Manifest.permission.CAMERA)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(RequestPermission()) { wasGranted ->
        if (wasGranted) {
            Toast.makeText(context, "📸 Photo in 3..2..1", Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            val scope = rememberCoroutineScope()
            val snackbarHostState = snackbarHostState
            FloatingActionButton(onClick = {
                when (state.status) {
                    PermissionStatus.Granted -> {
                        Toast.makeText(context, "Photo in 4..3..2..1", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        if (state.status.shouldShowRationale) {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Permission required",
                                    actionLabel = "Settings"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    val intent = Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.packageName, null)
                                    )
                                    context.startActivity(intent)
                                }
                            }
                        } else {
                            launcher.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
            }) {
                Icon(Icons.Rounded.Favorite, contentDescription = null)
            }
        }) {

    }
}
