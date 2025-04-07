package be.marche.pinpoint.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import be.marche.pinpoint.navigation.AppDestination
import be.marche.pinpoint.navigation.AppNavHost
import be.marche.pinpoint.navigation.navigateSingleTopTo
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    allScreens: List<AppDestination>,
    iconsScreens: List<AppDestination>,
    onTabSelected: (AppDestination) -> Unit,
    currentScreen: AppDestination,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val toggleDrawer: () -> Unit = {
        scope.launch {
            drawerState.apply { if (isClosed) open() else close() }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    allScreens, onTabSelected, navController, currentScreen,
                    toggleDrawer = toggleDrawer
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppTabRow(
                    modifier = modifier,
                    allScreens = iconsScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen,
                    toggleDrawer = toggleDrawer,
                    scrollBehavior = scrollBehavior
                )
            })
        { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
            ScreenContent(modifier = Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTabRow(
    modifier: Modifier = Modifier,
    allScreens: List<AppDestination>,
    onTabSelected: (AppDestination) -> Unit,
    currentScreen: AppDestination,
    toggleDrawer: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = "PinPoint")
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = {
                toggleDrawer()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(27.dp)
                        .clickable {
                            toggleDrawer()
                        }
                )
            }
        },
        actions = {
            allScreens.forEach { screen ->
                IconButton(onClick = { onTabSelected(screen) }) {
                    Icon(imageVector = screen.icon, contentDescription = screen.name)
                }
            }
        }
    )
}

@Composable
fun DrawerContent(
    allScreens: List<AppDestination>,
    onTabSelected: (AppDestination) -> Unit,
    navController: NavHostController,
    currentScreen: AppDestination,
    toggleDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "App name",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
    HorizontalDivider()
    allScreens.forEach { screen ->
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.name
                )
            },
            label = {
                Text(
                    text = screen.name,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp)
                )
            },
            selected = false,
            onClick = {
                onTabSelected(screen)
                navController.navigateSingleTopTo(screen.route)
                toggleDrawer()
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {

}

@Composable
private fun RallyTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text.uppercase(Locale.getDefault()), color = tabTintColor)
        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
