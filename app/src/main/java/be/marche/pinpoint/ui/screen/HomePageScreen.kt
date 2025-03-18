package be.marche.pinpoint.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomePageScreen(
    onClickSeeAllAccounts: () -> Unit = {},
    onClickSeeAllBills: () -> Unit = {},
    onClick: (String) -> Unit = {},
) {

    Text(text = "Homepage")
}
