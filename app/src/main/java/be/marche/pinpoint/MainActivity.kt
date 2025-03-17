package be.marche.pinpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.marche.pinpoint.navigation.HomePage
import be.marche.pinpoint.navigation.appIconsScreens
import be.marche.pinpoint.navigation.appTabRowScreens
import be.marche.pinpoint.navigation.navigateSingleTopTo
import be.marche.pinpoint.permission.PermissionUtil
import be.marche.pinpoint.ui.components.DrawScreen
import be.marche.pinpoint.ui.theme.PinPointTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCompat.requestPermissions(this, PermissionUtil.listOfPermissions, 100)

        setContent {
            KoinContext {
                StartApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartApp() {
    PinPointTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            appTabRowScreens.find { it.route == currentDestination?.route } ?: HomePage

        DrawScreen(
            allScreens = appTabRowScreens,
            iconsScreens = appIconsScreens,
            onTabSelected = { newScreen ->
                navController.navigateSingleTopTo(newScreen.route)
            },
            currentScreen = currentScreen,
            navController = navController,
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    StartApp()
}
