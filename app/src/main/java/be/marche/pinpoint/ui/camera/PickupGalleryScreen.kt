package be.marche.pinpoint.ui.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrowseGallery
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import be.marche.pinpoint.ui.components.IconButtonWithText

@Composable
fun ImageFromGalleryContent(fileUri: MutableState<Uri>) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                fileUri.value = uri
            }
        }
    IconButtonWithText(
        text = "Prendre une image depuis la gallerie",
        icon = Icons.Rounded.BrowseGallery,
        onClick = {
            pickMedia.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
            )
        })
}
