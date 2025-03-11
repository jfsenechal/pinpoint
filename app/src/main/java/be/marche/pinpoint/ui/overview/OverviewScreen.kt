package be.marche.pinpoint.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun OverviewScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Spacer(Modifier.height(RallyDefaultPadding))
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}

@Composable
fun AccountsScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onAccountClick: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {

        Spacer(Modifier.height(RallyDefaultPadding))
        Spacer(Modifier.height(RallyDefaultPadding))
    }
}

/**
 * Detail screen for a single account.
 */
@Composable
fun SingleAccountScreen(
    accountType: String? = "jf"
) {
    val account = remember(accountType) { "admin" }

}

private val RallyDefaultPadding = 12.dp

private const val SHOWN_ITEMS = 3
