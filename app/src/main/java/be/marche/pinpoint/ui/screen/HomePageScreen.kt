package be.marche.pinpoint.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import be.marche.pinpoint.ui.components.IconButtonWithText
import be.marche.pinpoint.ui.components.TitleWithDivider

@Composable
fun HomePageScreen(
    onClick: () -> Unit = {},
) {
    Column {
        TitleWithDivider("Homepage")

        IconButtonWithText(
            text = "Ajouter un objet",
            icon = Icons.Rounded.Add,
            onClick = {
                onClick()
            })
    }
}
