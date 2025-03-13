package be.marche.pinpoint.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.marche.pinpoint.geolocation.LocationManager
import be.marche.pinpoint.geolocation.LocationService

private val RallyDefaultPadding = 12.dp

@SuppressLint("MissingPermission")

@Composable
fun HomePageScreen(
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
        Text("Overview screen")
        Spacer(Modifier.height(RallyDefaultPadding))

        var locationText by remember {
            mutableStateOf("")
        }

        Text(text = locationText)

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            locationManager.getLocation { latitude, longitude ->
                locationText = "Lat: ${latitude} Lng : ${longitude}"
            }
        }) {
            Text(text = "Get Location")
        }

        Spacer(modifier = Modifier.height(50.dp))

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
}
