package be.marche.pinpoint.ui.screen

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
import be.marche.pinpoint.geolocation.LocationManager
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size

private val RallyDefaultPadding = 12.dp

@Composable
fun PickupMediaScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {

    val context = LocalContext.current

    val locationManager by lazy {
        LocationManager(context)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Spacer(Modifier.height(RallyDefaultPadding))
        Text("Pickup screen")

        DisplayImage()

        BtnsMedia()

    }
}

@Composable
private fun DisplayImage() {

    val painterStateFlow = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("xx")
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
private fun BtnsMedia() {
    Column(modifier = Modifier.wrapContentWidth()) {
        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = {

            }) {
            Text(text = "Pick image")
        }
    }
}
