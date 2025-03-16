package be.marche.pinpoint.ui.screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.camera.CameraViewModel
import be.marche.pinpoint.geolocation.LocationManager
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import org.koin.androidx.compose.koinViewModel

private val RallyDefaultPadding = 12.dp

@Composable
fun PickupMediaScreen(

) {

    val context = LocalContext.current

    val locationManager by lazy {
        LocationManager(context)
    }

    val cameraViewModel: CameraViewModel = koinViewModel()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            cameraViewModel.fileUri.value = uri
        }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Spacer(Modifier.height(RallyDefaultPadding))
        Text("Pickup screen")

        DisplayImage(cameraViewModel)

        BtnsMedia(pickMedia)

    }
}

@Composable
private fun DisplayImage(cameraViewModel: CameraViewModel) {

    val painterStateFlow = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cameraViewModel.fileUri.value)
            .size(Size.ORIGINAL)
            .build()
    )

    val imageState = painterStateFlow.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(MaterialTheme.colorScheme.primaryContainer)

    ) {
        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                painter = painterStateFlow
            )
        }
    }
}

@Composable
private fun BtnsMedia(pickMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    Column(modifier = Modifier.wrapContentWidth()) {
        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = {
                pickMedia.launch(
                    PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        .build()
                )

            }) {
            Text(text = "Pick image")
        }
    }
}
