package be.marche.pinpoint.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.rounded.Details
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

sealed interface AppDestination {
    val icon: ImageVector
    val name: String
    val route: String
}

object HomePage : AppDestination {
    override val icon = Icons.Rounded.Home
    override val name = "Accueil"
    override val route = "homepage"
}

object CategoryList : AppDestination {
    override val icon = Icons.AutoMirrored.Rounded.List
    override val name = "Liste des catégories"
    override val route = "category_list"
}

object CategoryShow : AppDestination {
    override val icon = Icons.Rounded.Details
    override val name = "Show category"
    override val route = "category_show"
    const val categoryTypeArg = "category_id"
    val routeWithArgs = "${route}/{$categoryTypeArg}"
    val arguments = listOf(
        navArgument(categoryTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://${route}/{$categoryTypeArg}" }
    )
}

object ItemList : AppDestination {
    override val icon = Icons.Rounded.Search
    override val name = "Liste des objets"
    override val route = "item_list"
    const val categoryTypeArg = "category_id"
    val routeWithArgs = "${route}/{$categoryTypeArg}"
    val arguments = listOf(
        navArgument(categoryTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://${route}/{$categoryTypeArg}" }
    )
}

object ItemNew : AppDestination {
    override val icon = Icons.Filled.Add
    override val name = "Nouvel objet"
    override val route = "item_new"
    const val categoryTypeArg = "category_id"
    val routeWithArgs = "${route}/{$categoryTypeArg}"
    val arguments = listOf(
        navArgument(categoryTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://${route}/{$categoryTypeArg}" }
    )
}

object ItemShow : AppDestination {
    override val icon = Icons.Filled.Info
    override val name = "Détails objet"
    override val route = "item_show"
    const val itemTypeArg = "item_id"
    val routeWithArgs = "${route}/{$itemTypeArg}"
    val arguments = listOf(
        navArgument(itemTypeArg) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://${route}/{$itemTypeArg}" }
    )
}

object Sync : AppDestination {
    override val icon = Icons.Filled.Sync
    override val name = "Synchronisation"
    override val route = "sync"
}

object Permissions : AppDestination {
    override val icon = Icons.Filled.Lock
    override val name = "Permissions"
    override val route = "permissions"
}

// Screens to be displayed in the top RallyTabRow
val appTabRowScreens = listOf(HomePage, CategoryList, Sync, Permissions)
val appIconsScreens = listOf(HomePage, CategoryList, Sync)
