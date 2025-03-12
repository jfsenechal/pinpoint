package be.marche.pinpoint.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

/**
 * Contract for information needed on every Rally navigation destination
 */
sealed interface AppDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object Overview : AppDestination {
    override val icon = Icons.Filled.Call
    override val route = "overview"
}

object Accounts : AppDestination {
    override val icon = Icons.Filled.AccountBox
    override val route = "accounts"
}

object Mars : AppDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "mars"
}

object Permissions : AppDestination {
    override val icon = Icons.Filled.Call
    override val route = "permissions"
}

data object SingleAccount : AppDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
    override val icon = Icons.Filled.Done
    override val route = "single_account"
    const val accountTypeArg = "account_type"
    val routeWithArgs = "$route/{$accountTypeArg}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
    )
}

// Screens to be displayed in the top RallyTabRow
val appTabRowScreens = listOf(Overview, Accounts, Mars, Permissions)