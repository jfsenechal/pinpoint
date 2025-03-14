package be.marche.pinpoint.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import be.marche.pinpoint.permission.RequiredPermission
import be.marche.pinpoint.sync.SyncViewModel
import be.marche.pinpoint.ui.mars.MarsScreen
import be.marche.pinpoint.ui.overview.SingleAccountScreen
import be.marche.pinpoint.ui.screen.CategoryListScreen
import be.marche.pinpoint.ui.screen.CategoryShowScreen
import be.marche.pinpoint.ui.screen.HomePageScreen
import be.marche.pinpoint.ui.screen.ItemListScreen
import be.marche.pinpoint.ui.screen.ItemNewScreen
import be.marche.pinpoint.ui.screen.ItemShowScreen
import be.marche.pinpoint.viewModel.MarsViewModel

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
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(CategoryList.route)
                },
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Mars.route) {
            val marsViewModel: MarsViewModel = viewModel()
            MarsScreen(
                marsUiState = marsViewModel.marsUiState,
            )
        }

        composable(route = CategoryList.route) {
          //  val syncViewModel: SyncViewModel = viewModel()

            CategoryListScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = CategoryShow.route) {
            CategoryShowScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = ItemList.route) {
            ItemListScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = ItemNew.route) {
            ItemNewScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = ItemShow.route) {
            ItemShowScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }

        composable(route = Permissions.route) {
            RequiredPermission()
        }

        composable(
            route = SingleAccount.routeWithArgs,
            arguments = SingleAccount.arguments,
            deepLinks = SingleAccount.deepLinks
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(accountType)
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

private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}
