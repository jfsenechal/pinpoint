package be.marche.pinpoint.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import be.marche.pinpoint.permission.RequiredPermission
import be.marche.pinpoint.ui.category.CategoryListScreen
import be.marche.pinpoint.ui.category.CategoryShowScreen
import be.marche.pinpoint.ui.item.ItemListScreen
import be.marche.pinpoint.ui.item.ItemNewScreen
import be.marche.pinpoint.ui.item.ItemShowScreen
import be.marche.pinpoint.ui.screen.HomePageScreen
import be.marche.pinpoint.ui.screen.SyncScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomePage.route,
        modifier = modifier
    ) {
        composable(route = HomePage.route) {
            HomePageScreen(
                onClick = {
                    navController.navigateSingleTopTo(CategoryList.route)
                }
            )
        }

        composable(route = Sync.route) {
            SyncScreen()
        }

        composable(route = CategoryList.route) {
            CategoryListScreen(
                onClickAdd = { accountType ->
                    navController.navigateToSingleAccount(ItemNew.route, accountType)
                },
                onClickShow = { accountType ->
                    navController.navigateToSingleAccount(CategoryShow.route, accountType)
                })
        }

        composable(
            route = CategoryShow.routeWithArgs,
            arguments = CategoryShow.arguments,
            deepLinks = CategoryShow.deepLinks
        ) { navBackStackEntry ->
            val categoryId =
                navBackStackEntry.arguments?.getInt(CategoryShow.categoryTypeArg)
            CategoryShowScreen(categoryId, onClick = { accountType ->
                navController.navigateToSingleAccount(ItemNew.route, accountType)
            })
        }

        composable(route = ItemList.route) {
            ItemListScreen(
                onClick = { accountType ->
                    navController.navigateToSingleAccount(ItemShow.route, accountType)
                }
            )
        }

        composable(
            route = ItemNew.routeWithArgs,
            arguments = ItemNew.arguments,
            deepLinks = ItemNew.deepLinks
        ) { navBackStackEntry ->
            val categoryId =
                navBackStackEntry.arguments?.getInt(CategoryShow.categoryTypeArg)
            ItemNewScreen(
                categoryId = categoryId,
                onClick = { accountType ->
                navController.navigateToSingleAccount(ItemNew.route, accountType)
            })
        }

        composable(
            route = ItemShow.routeWithArgs,
            arguments = ItemShow.arguments,
            deepLinks = ItemShow.deepLinks
        ) { navBackStackEntry ->
            val itemId =
                navBackStackEntry.arguments?.getInt(ItemShow.itemTypeArg)
            ItemShowScreen(itemId, onClick = { accountType ->
                navController.navigateToSingleAccount(ItemShow.route, accountType)
            })
        }

        composable(route = Permissions.route) {
            RequiredPermission()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

private fun NavHostController.navigateToSingleAccount(route: String, accountType: Int) {
    this.navigateSingleTopTo("${route}/$accountType")
}
