package be.marche.pinpoint.camera

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CameraViewModel() : ViewModel() {
    val fileUri = mutableStateOf<Uri?>(null)
}
