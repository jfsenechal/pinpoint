package be.marche.pinpoint.ui.item

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import be.marche.pinpoint.geolocation.LocationManager
import be.marche.pinpoint.geolocation.LocationService
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.ui.components.IconButtonWithText
import be.marche.pinpoint.ui.screen.ImageFromCameraContent
import be.marche.pinpoint.ui.screen.ImageFromGalleryContent
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import org.koin.androidx.compose.koinViewModel

/**
 * get location
 * get image from camera or gallery
 * add comment
 * insert into db
 *
 */

private val RallyDefaultPadding = 12.dp

@Composable
fun ItemNewScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val itemViewModel: ItemViewModel = koinViewModel()

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Item new" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Item new")
        Spacer(Modifier.height(RallyDefaultPadding))
        LocationContent(context, itemViewModel.location)
        Spacer(Modifier.height(RallyDefaultPadding))
        DisplayImage(fileUri = itemViewModel.fileUri)
        Spacer(Modifier.height(RallyDefaultPadding))
        ImageFromCameraContent(context, fileUri = itemViewModel.fileUri)
        Spacer(Modifier.height(RallyDefaultPadding))
        ImageFromGalleryContent(fileUri = itemViewModel.fileUri)
        IconButtonWithText(
            text = "Ajouter",
            icon = Icons.Rounded.Add,
            onClick = {
                itemViewModel.location.value?.let { loc ->
                    itemViewModel.addItem(itemViewModel.location.value!!, "")
                    Toast.makeText(context, "item added", Toast.LENGTH_LONG).show()
                }
            })
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun LocationContent(context: Context, location: MutableState<Location?>) {

    val locationManager by lazy {
        LocationManager(context)
    }
    Text("Géolocalisation")
    Spacer(Modifier.height(RallyDefaultPadding))

    var locationText by remember {
        mutableStateOf("-------")
    }

    Text(text = locationText)

    Spacer(modifier = Modifier.height(RallyDefaultPadding))

    IconButtonWithText(
        text = "Rafraîchir position",
        icon = Icons.Rounded.Place,
        onClick = {
            locationManager.getLocation { newLocation ->
                location.value = newLocation
                locationText = "Lat: ${newLocation.latitude} Lng : ${newLocation.longitude}"
            }
        })
}

@Composable
private fun DisplayImage(fileUri: MutableState<Uri>) {
    val painterStateFlow = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(fileUri.value)
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
        // if (imageState is AsyncImagePainter.State.Success) {}
        // if (fileUri.value != Uri.EMPTY) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            painter = painterStateFlow
        )
    }
}

@Composable
private fun BtnsStartTracker(context: Context) {
    Button(onClick = {
        Intent(context, LocationService::class.java)
            .also {
                it.action = LocationService.ACTION.START.name
                context.startService(it)
            }
    }) {
        Text(text = "Start tracking")
    }

    Spacer(modifier = Modifier.height(50.dp))

    Button(onClick = {
        Intent(context, LocationService::class.java)
            .also {
                it.action = LocationService.ACTION.STOP.name
                context.startService(it)
            }
    }) {
        Text(text = "Stop tracking")
    }
}