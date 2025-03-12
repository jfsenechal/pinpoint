package be.marche.pinpoint.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text

class HomeScreen(val navController: NavController) {

    @Composable
    fun Main() {
        Text(
            text = "Homepage ",
        )
    }
}
