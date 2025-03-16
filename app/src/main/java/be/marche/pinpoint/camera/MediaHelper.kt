package be.marche.pinpoint.camera

import android.net.Uri
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class MediaHelper(caller: ActivityResultCaller, private val onMediaPicked: (Uri?) -> Unit) {

    private val pickMedia: ActivityResultLauncher<PickVisualMediaRequest> =
        caller.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            onMediaPicked(uri)
        }

    fun launchPicker() {
        pickMedia.launch(
            PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
        )
    }
}
