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
import be.marche.pinpoint.database.DatabaseProvider
import be.marche.pinpoint.navigation.HomePage
import be.marche.pinpoint.navigation.appTabRowScreens
import be.marche.pinpoint.navigation.navigateSingleTopTo
import be.marche.pinpoint.permission.PermissionUtil
import be.marche.pinpoint.ui.components.DrawScreen
import be.marche.pinpoint.ui.theme.PinPointTheme
import be.marche.pinpoint.viewModel.ItemViewModel

/**
 * This Activity recreates part of the Rally Material Study from
 * https://material.io/design/material-studies/rally.html
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        ActivityCompat.requestPermissions(this, PermissionUtil.listOfPermissions, 100)

        // Access the database instance and DAO
        val database = DatabaseProvider.getDatabase(applicationContext)
        val itemDao = database.itemDao()

        // Create the ViewModel
        val viewModel = ItemViewModel(itemDao)
        setContent {
            StartApp()
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
            onTabSelected = { newScreen ->
                navController.navigateSingleTopTo(newScreen.route)
            },
            currentScreen = currentScreen,
            navController = navController,
        )

        /*  Scaffold(
              topBar = {
                  AppTabRow(
                      allScreens = appTabRowScreens,
                      onTabSelected = { newScreen ->
                          navController.navigateSingleTopTo(newScreen.route)
                      },
                      currentScreen = currentScreen
                  )
              }
          ) { innerPadding ->
              AppNavHost(
                  navController = navController,
                  modifier = Modifier.padding(innerPadding)
              )
          }*/
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    StartApp()
}

